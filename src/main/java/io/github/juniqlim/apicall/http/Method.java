package io.github.juniqlim.apicall.http;

import org.springframework.http.HttpMethod;

public enum Method {
    GET(HttpMethod.GET),
    HEAD(HttpMethod.HEAD),
    POST(HttpMethod.POST),
    PUT(HttpMethod.PUT),
    PATCH(HttpMethod.PATCH),
    DELETE(HttpMethod.DELETE),
    OPTIONS(HttpMethod.OPTIONS),
    TRACE(HttpMethod.TRACE);

    HttpMethod httpMethod;

    Method(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }
}
