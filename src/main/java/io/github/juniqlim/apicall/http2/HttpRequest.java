package io.github.juniqlim.apicall.http2;

import io.github.juniqlim.apicall.http.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest<Q>  {
    private final Method httpMethod;
    private final String url;
    private final Map<String, String> header;
    private final Q request;

    public HttpRequest(Method httpMethod, String url, Map<String, String> header, Q request) {
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

    public Object request() {
        return request;
    }

    public static class Smart {
        public static HttpRequest to(String url) {
            return new HttpRequest(Method.GET, url, new HashMap<String, String>(), null);
        }

        public static HttpRequest to(Method method, String url) {
            return new HttpRequest(method, url, new HashMap<String, String>(), null);
        }

        public static HttpRequest to(Method method, String url, Map<String, String> header) {
            return new HttpRequest(method, url, header, null);
        }

        public static <Q> HttpRequest to(Method method, String url, Map<String, String> header, Q request) {
            return new HttpRequest(method, url, header, request);
        }

        public static <Q> HttpRequest to(Method method, String url, Q request) {
            return new HttpRequest(method, url, new HashMap<String, String>(), request);
        }
    }
}
