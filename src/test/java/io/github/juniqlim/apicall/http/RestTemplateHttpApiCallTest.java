package io.github.juniqlim.apicall.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

class RestTemplateHttpApiCallTest {
    private static HttpApiCall httpApiCall;

    @BeforeAll
    static void setup() {
        httpApiCall = new RestTemplateHttpApiCall(new RestTemplate(), new ObjectMapper());
    }

    @Test
    void 요청과_응답없이_API호출() {
        try {
            httpApiCall.callApi(
                new HttpRequestWithoutResponse(Method.DELETE, "https://gorest.co.in/public/v2/users/2030000",
                    header()));
        } catch (HttpApiCallException e) {
            assertThat(e.httpResponse().httpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void 요청없이_API호출() {
        Response[] responses = httpApiCall.callApi(
            new HttpRequest<>(Method.GET, "https://gorest.co.in/public/v2/users", Response[].class));

        Assertions.assertThat(responses).isNotNull();
    }

    @Test
    void API호출() {
        Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");

        Response createdUser = httpApiCall.callApi(
            new HttpRequest<>(Method.POST, "https://gorest.co.in/public/v2/users", header(), request,
                Response.class));

        assertThat(createdUser.getId()).isGreaterThan(0);

        Response findedUser = findUsers().get(0);
        deleteUserForRegressionTest(findedUser.getId());
    }

    List<Response> findUsers() {
        Response[] responses = httpApiCall.callApi(
            new HttpRequest<>(Method.GET, "https://gorest.co.in/public/v2/users?name=juniq", header(),
                Response[].class));

        return Arrays.stream(responses).collect(Collectors.toList());
    }

    void deleteUserForRegressionTest(int id) {
        httpApiCall.callApi(
            new HttpRequestWithoutResponse<>(Method.DELETE, "https://gorest.co.in/public/v2/users/" + id, header()));
    }

    private Map<String, String> header() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer d6d64785492c26bf654f88db031777b7802821ca14e4ee62acd12acc29470d1f");
        return map;
    }
}
