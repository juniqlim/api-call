package io.github.juniqlim.apicall.http;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpMethod;

/**
 * interface
 */
public class HttpRequest<Q, S> {
    private final HttpMethod httpMethod;
    private final String url;
    private final Map<String, String> header;
    private final Q request;
    private final Class<S> responseType;

    private HttpRequest(HttpMethod httpMethod, String url, Map<String, String> header, Q request, Class<S> responseType) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.header = header;
        this.request = request;
        this.responseType = responseType;
    }

    public static <Q, S> HttpRequest<Q, S> of(HttpMethod httpMethod, String url, Map<String, String> header, Q request,
        Class<S> responseType) {
        return new HttpRequest<>(httpMethod, url, header, request, responseType);
    }

    public static <Q, S> HttpRequest<Q, S> of(HttpMethod httpMethod, String url, Map<String, String> header,
        Class<S> responseType) {
        return new HttpRequest<>(httpMethod, url, header, null, responseType);
    }

    public static <Q, S> HttpRequest<Q, S> of(HttpMethod httpMethod, String url, Q request,
        Class<S> responseType) {
        return new HttpRequest<>(httpMethod, url, new HashMap<>(), request, responseType);
    }

    public static <Q, S> HttpRequest<Q, S> of(HttpMethod httpMethod, String url, Class<S> responseType) {
        return new HttpRequest<>(httpMethod, url, new HashMap<>(), null, responseType);
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

    public Class<S> responseType() {
        return responseType;
    }
}
