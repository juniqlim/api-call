package io.github.juniqlim.apicall.http;

/**
 * interface
 */
public interface HttpApiCall {
    <Q, S> S callApi(HttpRequest<Q, S> request);
    <Q> void callApi(HttpRequestWithoutResponse<Q> response);
}
