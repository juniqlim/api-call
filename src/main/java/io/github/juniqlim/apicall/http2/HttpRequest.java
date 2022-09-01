package io.github.juniqlim.apicall.http2;

import io.github.juniqlim.apicall.http.Method;
import java.util.HashMap;
import java.util.Map;

public interface HttpRequest<Q> {
    Method httpMethod();
    String url();
    Map<String, String> header();
    Q request();

    class DefaultHttpRequest<Q> implements HttpRequest {
        private final Method httpMethod;
        private final String url;
        private final Map<String, String> header;
        private final Q request;

        public DefaultHttpRequest(Method httpMethod, String url, Map<String, String> header, Q request) {
            this.httpMethod = httpMethod;
            this.url = url;
            this.header = header;
            this.request = request;
        }

        @Override
        public Method httpMethod() {
            return httpMethod;
        }

        @Override
        public String url() {
            return url;
        }

        @Override
        public Map<String, String> header() {
            return header;
        }

        @Override
        public Object request() {
            return request;
        }
    }

    class Smart {
        public static DefaultHttpRequest to(String url) {
            return new DefaultHttpRequest(Method.GET, url, new HashMap<String, String>(), null);
        }

        public static DefaultHttpRequest to(Method method, String url) {
            return new DefaultHttpRequest(method, url, new HashMap<String, String>(), null);
        }

        public static DefaultHttpRequest to(Method method, String url, Map<String, String> header) {
            return new DefaultHttpRequest(method, url, header, null);
        }

        public static <Q> DefaultHttpRequest to(Method method, String url, Map<String, String> header, Q request) {
            return new DefaultHttpRequest(method, url, header, request);
        }

        public static <Q> DefaultHttpRequest to(Method method, String url, Q request) {
            return new DefaultHttpRequest(method, url, new HashMap<String, String>(), request);
        }
    }
}
