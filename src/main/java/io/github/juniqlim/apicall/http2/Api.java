package io.github.juniqlim.apicall.http2;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.juniqlim.apicall.http.HttpApiCallResult;
import io.github.juniqlim.apicall.http.HttpResponse;
import io.github.juniqlim.apicall.http.logging.HttpLogging;
import io.github.juniqlim.apicall.http.logging.HttpLogging.SystemOutPrintHttpLogging;
import io.github.juniqlim.apicall.serialization.DeserializedObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface Api<S> {
    void call();
    S response(Class<S> responseType) throws JsonProcessingException;

    class Http<S> implements Api<S> {
        private final HttpRequest request;
        private final WebClient webClient;
        private final HttpLogging httpLogging;

        public Http(HttpRequest request, WebClient webClient, HttpLogging httpLogging) {
            this.request = request;
            this.webClient = webClient;
            this.httpLogging = httpLogging;
        }

        public void call() {
            callServer();
        }

        public S response(Class<S> responseType) throws JsonProcessingException {
            ResponseEntity<String> response = callServer();

            if (responseType == String.class) {
                return (S) response.getBody();
            }
            return new DeserializedObject<>(response.getBody(), responseType).object();
        }

        private ResponseEntity<String> callServer() {
            ResponseEntity<String> response = callByWebClient();

            httpLogging.log(HttpApiCallResult.of(request.httpMethod().httpMethod(), request.url(), request.header(),
                request.request(), new HttpResponse(response.getStatusCode(), response.getBody())));
            return response;
        }

        private ResponseEntity<String> callByWebClient() {
            if (request.request() == null) {
                return callWithoutRequestBody();
            }
            return callWithRequstBody();
        }

        private ResponseEntity<String> callWithRequstBody() {
            return webClient.method(request.httpMethod().httpMethod()).uri(request.url())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setAll(request.header()))
                .body(BodyInserters.fromValue(request.request()))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty())
                .toEntity(String.class)
                .block();
        }

        private ResponseEntity<String> callWithoutRequestBody() {
            return webClient.method(request.httpMethod().httpMethod()).uri(request.url())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setAll(request.header()))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty())
                .toEntity(String.class)
                .block();
        }
    }

    class Smart<S> {
        public Api<S> to(HttpRequest request) {
            return to(request, WebClient.builder().build());
        }

        public Api<S> to(HttpRequest request, WebClient webClient) {
            return new Http<>(request, webClient, new SystemOutPrintHttpLogging());
        }

        public Api<S> to(HttpRequest request, HttpLogging httpLogging) {
            return new Http<>(request, WebClient.builder().build(), httpLogging);
        }
    }
}
