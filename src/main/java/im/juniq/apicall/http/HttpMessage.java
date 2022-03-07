package im.juniq.apicall.http;

import java.util.Map;
import org.springframework.http.HttpMethod;

public class HttpMessage {
    private final HttpMethod httpMethod;
    private final String url;
    private final Map<String, String> header;
    private final Object request;
    private final HttpResponse response;

    private HttpMessage(HttpMethod httpMethod, String url, Map<String, String> header, Object request,
        HttpResponse response) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.header = header;
        this.request = request;
        this.response = response;
    }

    public static HttpMessage of(HttpMethod httpMethod, String url, Map<String, String> header, Object request,
        HttpResponse response) {
        return new HttpMessage(httpMethod, url, header, request, response);
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

    public Object request() {
        return request;
    }

    public HttpResponse response() {
        return response;
    }
}
