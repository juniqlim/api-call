package io.github.juniqlim.apicall.http;

import java.util.HashMap;
import java.util.Map;

/**
 * interface
 */
public class HttpRequest<Q, S> {
    private final Method httpMethod;
    private final String url;
    private final Map<String, String> header;
    private final Q request;
    private final Class<S> responseType;

    public HttpRequest(Method httpMethod, String url, Class<S> responseType) {
        this(httpMethod, url, new HashMap<>(), null, responseType);
    }

    public HttpRequest(Method httpMethod, String url, Map<String, String> header,
        Class<S> responseType) {
        this(httpMethod, url, header, null, responseType);
    }

    public HttpRequest(Method httpMethod, String url, Map<String, String> header, Q request,
        Class<S> responseType) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.header = header;
        this.request = request;
        this.responseType = responseType;
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

    public Class<S> responseType() {
        return responseType;
    }
}
