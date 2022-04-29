package io.github.juniqlim.apicall.http;

/**
 * interface
 */
public class HttpApiCallException extends RuntimeException {
    private final HttpResponse httpResponse;

    public HttpApiCallException(HttpResponse httpResponse, String message) {
        super(message);
        this.httpResponse = httpResponse;
    }

    public HttpResponse httpResponse() {
        return httpResponse;
    }
}
