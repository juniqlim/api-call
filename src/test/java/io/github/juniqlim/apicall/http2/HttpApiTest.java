package io.github.juniqlim.apicall.http2;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.juniqlim.apicall.http.HttpApiCallException;
import io.github.juniqlim.apicall.http.Method;
import io.github.juniqlim.apicall.http.Request;
import io.github.juniqlim.apicall.http.Response;
import io.github.juniqlim.apicall.http2.HttpApi.DefaultHttpApi;
import io.github.juniqlim.apicall.http2.HttpRequest.DefaultHttpRequest;
import io.github.juniqlim.apicall.http2.HttpRequest.Smart;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HttpApiTest {
    @Test
    void callWithoutRequest() throws JsonProcessingException {
        Response[] responses = new DefaultHttpApi.Smart<Response[]>().to(
            new Smart().to("https://gorest.co.in/public/v2/users")).response(Response[].class);

        Assertions.assertThat(responses).isNotNull();
    }

    @Test
    void callWithoutResponse() {
        try {
            new DefaultHttpApi.Smart<>().to(new Smart().to(Method.DELETE, "https://gorest.co.in/public/v2/users/2030000")).call();
        } catch (HttpApiCallException e) {
            assertThat(e.httpResponse().body()).isEqualTo("{\"message\":\"Resource not found\"}");
        }
    }

    @Test
    void call() throws JsonProcessingException {
        Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");

        Response createdUser = new DefaultHttpApi.Smart<Response>().to(
                new DefaultHttpRequest(Method.POST, "https://gorest.co.in/public/v2/users", header(), request))
            .response(Response.class);

        assertThat(createdUser.getId()).isGreaterThan(0);

        Response findedUser = findUsers().get(0);
        deleteUserForRegressionTest(findedUser.getId());
    }

    private Map<String, String> header() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer d6d64785492c26bf654f88db031777b7802821ca14e4ee62acd12acc29470d1f");
        return map;
    }

    List<Response> findUsers() throws JsonProcessingException {
        Response[] responses = new DefaultHttpApi.Smart<Response[]>().to(
            new Smart().to(Method.GET, "https://gorest.co.in/public/v2/users?name=juniq", header())).response(Response[].class);

        return Arrays.stream(responses).collect(Collectors.toList());
    }

    void deleteUserForRegressionTest(int id) {
        new DefaultHttpApi.Smart<>().to(new Smart().to(Method.DELETE, "https://gorest.co.in/public/v2/users/" + id, header())).call();
    }
}
