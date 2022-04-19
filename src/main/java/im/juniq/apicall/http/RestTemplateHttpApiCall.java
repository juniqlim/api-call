package im.juniq.apicall.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import im.juniq.apicall.http.logging.HttpLogging;
import im.juniq.apicall.http.logging.HttpLogging.SystemOutPrintHttpLogging;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestTemplateHttpApiCall implements HttpApiCall {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpLogging httpLogging;

    private RestTemplateHttpApiCall(RestTemplate restTemplate, ObjectMapper objectMapper, HttpLogging httpLogging) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.httpLogging = httpLogging;
    }

    public static RestTemplateHttpApiCall of(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new RestTemplateHttpApiCall(restTemplate, objectMapper, new SystemOutPrintHttpLogging());
    }

    public static RestTemplateHttpApiCall of(RestTemplate restTemplate, ObjectMapper objectMapper, HttpLogging httpLogging) {
        return new RestTemplateHttpApiCall(restTemplate, objectMapper, httpLogging);
    }

    @Override
    public String callApi(String url) {
        return callApi(url, new HashMap<>());
    }

    @Override
    public <S>S callApi(String url, Class<S> clazz) {
        return parseResponseBody(callApi(url, new HashMap<>()), clazz);
    }

    @Override
    public String callApi(String url, Map<String, String> headers) {
        return callApi(HttpMethod.GET, url, headers, null);
    }

    @Override
    public <S>S callApi(String url, Map<String, String> headers, Class<S> clazz) {
        return parseResponseBody(callApi(HttpMethod.GET, url, headers, null), clazz);
    }

    @Override
    public <Q> String callApi(HttpMethod httpMethod, String url, Map<String, String> header, Q request) {
        HttpResponse response = sendHttpRequest(httpMethod, url, header, request);
        log(HttpApiCallResult.of(httpMethod, url, header, request, response));
        return response.body();
    }

    @Override
    public <Q, S>S callApi(HttpMethod httpMethod, String url, Map<String, String> header, Q request, Class<S> clazz) {
        HttpResponse response = sendHttpRequest(httpMethod, url, header, request);
        log(HttpApiCallResult.of(httpMethod, url, header, request, response));
        return parseResponseBody(response.body(), clazz);
    }

    private void log(HttpApiCallResult httpMethod) {
        if (httpMethod.response().isError()) {
            httpLogging.errorLog(httpMethod);
            throw new HttpApiCallException(httpMethod.response(), "Http request exception");
        }
        httpLogging.infoLog(httpMethod);
    }

    private HttpResponse sendHttpRequest(HttpMethod httpMethod, String url, Map<String, String> header,
        Object request) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, httpMethod,
                new HttpEntity<>(request, makeHeader(header)), String.class);
            return HttpResponse.of(response.getStatusCode(), response.getBody());
        } catch (HttpClientErrorException e) {
            return HttpResponse.of(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    private MultiValueMap<String, String> makeHeader(Map<String, String> header) {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        result.setAll(header);
        return result;
    }

    private <S> S parseResponseBody(String responseBody, Class<S> clazz) {
        return ResponseBodyParser.of(objectMapper).parse(responseBody, clazz);
    }
}
