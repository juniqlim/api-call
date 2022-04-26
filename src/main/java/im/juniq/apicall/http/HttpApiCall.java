package im.juniq.apicall.http;

public interface HttpApiCall {
    <Q, S> S callApi(HttpRequest<Q, S> request);
    <Q> void callApi(HttpRequestWithoutResponse<Q> response);
}
