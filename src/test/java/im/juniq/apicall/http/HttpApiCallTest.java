package im.juniq.apicall.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

class HttpApiCallTest {
    @Test
    void 등록_조회_삭제() {
        HttpApiCall httpApiCall = HttpApiCall.of(new RestTemplate(), new ObjectMapper());
        Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");

        httpApiCall.callApi(HttpMethod.POST, "https://gorest.co.in/public/v2/users", header(), request);

        Response response = get().get(0);
        assertThat(response.getId()).isNotZero();
        assertThat(response.getName()).isEqualTo("juniq");

        delete(response.getId());
    }

    void delete(int id) {
        HttpApiCall httpApiCall = HttpApiCall.of(new RestTemplate(), new ObjectMapper());
        httpApiCall.callApi(HttpMethod.DELETE, "https://gorest.co.in/public/v2/users/" + id, header(), null);
    }

    List<Response> get() {
        HttpApiCall httpApiCall = HttpApiCall.of(new RestTemplate(), new ObjectMapper());
        Response[] responses = httpApiCall.callApi("https://gorest.co.in/public/v2/users?name=juniq", header(),
            Response[].class);
        return Arrays.stream(responses).collect(Collectors.toList());
    }

    private Map<String, String> header() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer d6d64785492c26bf654f88db031777b7802821ca14e4ee62acd12acc29470d1f");
        return map;
    }
}
