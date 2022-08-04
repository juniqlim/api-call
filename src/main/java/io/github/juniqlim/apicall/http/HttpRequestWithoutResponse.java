package io.github.juniqlim.apicall.http;

import java.util.Map;

/**
 * interface
 */
public class HttpRequestWithoutResponse<Q> {
    private final Method httpMethod;
    private final String url;
    private final Map<String, String> header;
    private final Q request;

    public HttpRequestWithoutResponse(Method httpMethod, String url, Map<String, String> header) {
        this(httpMethod, url, header, null);
    }

    public HttpRequestWithoutResponse(Method httpMethod, String url, Map<String, String> header, Q request) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.header = header;
        this.request = request;
    }

    public Method httpMethod() {
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
