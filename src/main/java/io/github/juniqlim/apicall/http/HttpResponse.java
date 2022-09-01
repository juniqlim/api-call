package io.github.juniqlim.apicall.http;

import org.springframework.http.HttpStatus;

/**
 * interface
 */
public class HttpResponse {
    private final HttpStatus httpStatus;
    private final String body;

    public HttpResponse(HttpStatus httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public static HttpResponse of(HttpStatus httpStatus, String body) {
        return new HttpResponse(httpStatus, body);
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

    public String body() {
        return body;
    }

    public boolean isError() {
        return httpStatus.is4xxClientError() || httpStatus.is5xxServerError();
    }
}
