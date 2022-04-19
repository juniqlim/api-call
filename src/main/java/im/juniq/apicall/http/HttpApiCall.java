package im.juniq.apicall.http;

import java.util.Map;
import org.springframework.http.HttpMethod;

public interface HttpApiCall {
    String callApi(String url);
    String callApi(String url, Map<String, String> headers);
    <Q> String callApi(HttpMethod httpMethod, String url, Map<String, String> header, Q request);
    <S>S callApi(String url, Class<S> clazz);
    <S>S callApi(String url, Map<String, String> headers, Class<S> clazz);
    <Q, S>S callApi(HttpMethod httpMethod, String url, Map<String, String> header, Q request, Class<S> clazz);
}
