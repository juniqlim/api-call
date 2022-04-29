package io.github.juniqlim.apicall.http;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpMethod;

/**
 * interface
 */
public class HttpRequestWithoutResponse<Q> {
    private final HttpMethod httpMethod;
    private final String url;
    private final Map<String, String> header;
    private final Q request;

    private HttpRequestWithoutResponse(HttpMethod httpMethod, String url, Map<String, String> header, Q request) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.header = header;
        this.request = request;
    }

    public static <Q> HttpRequestWithoutResponse<Q> of(HttpMethod httpMethod, String url, Map<String, String> header,
        Q request) {
        return new HttpRequestWithoutResponse<>(httpMethod, url, header, request);
    }

    public static <Q> HttpRequestWithoutResponse<Q> of(HttpMethod httpMethod, String url, Map<String, String> header) {
        return new HttpRequestWithoutResponse<>(httpMethod, url, header, null);
    }

    public static <Q> HttpRequestWithoutResponse<Q> of(HttpMethod httpMethod, String url, Q request) {
        return new HttpRequestWithoutResponse<>(httpMethod, url, new HashMap<>(), request);
    }

    public static <Q> HttpRequestWithoutResponse<Q> of(HttpMethod httpMethod, String url) {
        return new HttpRequestWithoutResponse<>(httpMethod, url, new HashMap<>(), null);
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }

    public String url() {
        return url;
    }

    public Map<String, String> header() {
        return header;
    }

    public Q request() {
        return request;
    }
}
