package io.github.juniqlim.apicall.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.juniqlim.apicall.http.logging.HttpLogging;
import io.github.juniqlim.apicall.http.logging.HttpLogging.SystemOutPrintHttpLogging;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientHttpApiCall implements HttpApiCall {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final HttpLogging httpLogging;

    public WebClientHttpApiCall(WebClient webClient, ObjectMapper objectMapper, HttpLogging httpLogging) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.httpLogging = httpLogging;
    }

    public static WebClientHttpApiCall of(WebClient webClient, ObjectMapper objectMapper) {
        return new WebClientHttpApiCall(webClient, objectMapper, new SystemOutPrintHttpLogging());
    }

    public static WebClientHttpApiCall of(WebClient webClient, ObjectMapper objectMapper,
        HttpLogging httpLogging) {
        return new WebClientHttpApiCall(webClient, objectMapper, httpLogging);
    }

    @Override
    public <Q, S> S callApi(HttpRequest<Q, S> request) {
        return run(request.httpMethod(), request.url(), request.header(), request.request(), request.responseType());
    }

    @Override
    public <Q> void callApi(HttpRequestWithoutResponse<Q> response) {
        runWithoutResponse(response.httpMethod(), response.url(), response.header(), response.request());
    }

    private <Q, S> S run(HttpMethod httpMethod, String url, Map<String, String> header, Q request, Class<S> clazz) {
        HttpResponse response = sendHttpRequest(httpMethod, url, header, request);
        log(HttpApiCallResult.of(httpMethod, url, header, request, response));
        return parseResponseBody(response.body(), clazz);
    }

    private <Q> void runWithoutResponse(HttpMethod httpMethod, String url, Map<String, String> header, Q request) {
        HttpResponse response = sendHttpRequest(httpMethod, url, header, request);
        log(HttpApiCallResult.of(httpMethod, url, header, request, response));
    }

    private void log(HttpApiCallResult httpMethod) {
        if (httpMethod.response().isError()) {
            httpLogging.errorLog(httpMethod);
            throw new HttpApiCallException(httpMethod.response(),
                "Http request call exception - status: " + httpMethod.response().httpStatus() + ", response: "
                    + httpMethod.response().body());
        }
        httpLogging.infoLog(httpMethod);
    }

    private HttpResponse sendHttpRequestWithoutRequestBody(HttpMethod httpMethod, String url, Map<String, String> header) {
        try {
            ResponseEntity<String> response = webClient.method(httpMethod).uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setAll(header))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty())
                .toEntity(String.class)
                .block();
            return HttpResponse.of(response.getStatusCode(), response.getBody());
        } catch (HttpClientErrorException e) {
            return HttpResponse.of(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    private HttpResponse sendHttpRequest(HttpMethod httpMethod, String url, Map<String, String> header, Object request) {
        if (request == null) {
            return sendHttpRequestWithoutRequestBody(httpMethod, url, header);
        }
        try {
            ResponseEntity<String> response = webClient.method(httpMethod).uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setAll(header))
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.empty())
                .toEntity(String.class)
                .block();
            return HttpResponse.of(response.getStatusCode(), response.getBody());
        } catch (HttpClientErrorException e) {
            return HttpResponse.of(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @SuppressWarnings("unchecked")
    private <S> S parseResponseBody(String responseBody, Class<S> clazz) {
        if (clazz == String.class) {
            return (S) responseBody;
        }
        return ResponseBodyParser.of(objectMapper).parse(responseBody, clazz);
    }
}
