package im.juniq.apicall.http;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class HttpApiCall {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpLogging httpLogging;

    private HttpApiCall(RestTemplate restTemplate, ObjectMapper objectMapper, HttpLogging httpLogging) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.httpLogging = httpLogging;
    }

    public static HttpApiCall of(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new HttpApiCall(restTemplate, objectMapper, new SystemOutPrintHttpLogging());
    }

    public static HttpApiCall of(RestTemplate restTemplate, ObjectMapper objectMapper, HttpLogging httpLogging) {
        return new HttpApiCall(restTemplate, objectMapper, httpLogging);
    }

    public String callApi(String url) {
        return callApi(url, new HashMap<>());
    }

    public String callApi(String url, Map<String, String> headers) {
        return callApi(HttpMethod.GET, url, headers, null);
    }

    public <T>T callApi(String url, Map<String, String> headers, Class<T> clazz) {
        String responseBody = callApi(HttpMethod.GET, url, headers, null);
        return ResponseBodyParser.of(objectMapper).parse(responseBody, clazz);
    }

    public String callApi(HttpMethod httpMethod, String url, Map<String, String> header, Object request) {
        HttpResponse response = sendHttpRequest(httpMethod, url, header, request);
        if (response.isError()) {
            httpLogging.errorLog(HttpMessage.of(httpMethod, url, header, request, response));
            throw new HttpApiCallException(response, "Http request exception");
        }
        httpLogging.infoLog(HttpMessage.of(httpMethod, url, header, request, response));
        return response.body();
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

    private String stringOf(Object request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
