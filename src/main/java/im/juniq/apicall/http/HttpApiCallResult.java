package im.juniq.apicall.http;

import java.util.Map;
import org.springframework.http.HttpMethod;

public class HttpApiCallResult {
    private final HttpMethod httpMethod;
    private final String url;
    private final Map<String, String> header;
    private final Object request;
    private final HttpResponse response;

    private HttpApiCallResult(HttpMethod httpMethod, String url, Map<String, String> header, Object request,
        HttpResponse response) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.header = header;
        this.request = request;
        this.response = response;
    }

    public static HttpApiCallResult of(HttpMethod httpMethod, String url, Map<String, String> header, Object request,
        HttpResponse response) {
        return new HttpApiCallResult(httpMethod, url, header, request, response);
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
