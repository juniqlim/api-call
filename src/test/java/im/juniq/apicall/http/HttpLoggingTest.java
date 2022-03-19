package im.juniq.apicall.http;

import static org.assertj.core.api.Assertions.assertThatCode;

import im.juniq.apicall.http.logging.HttpLogging;
import im.juniq.apicall.http.logging.HttpLogging.SystemOutPrintHttpLogging;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class HttpLoggingTest {
    @Test
    void infoLog() {
        HttpLogging logging = new SystemOutPrintHttpLogging();
        Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");
        HttpResponse response = HttpResponse.of(HttpStatus.OK, "{\"code\": \"success\"}");
        HttpApiCallResult httpApiCallResult = HttpApiCallResult.of(HttpMethod.POST, "https://gorest.co.in/public/v2/users", header(), request, response);

        assertThatCode(() -> logging.infoLog(httpApiCallResult)).doesNotThrowAnyException();
    }

    @Test
    void errorLog() {
        HttpLogging logging = new SystemOutPrintHttpLogging();
        Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");
        HttpResponse response = HttpResponse.of(HttpStatus.OK, "{\"code\": \"success\"}");
        HttpApiCallResult httpApiCallResult = HttpApiCallResult.of(HttpMethod.POST, "https://gorest.co.in/public/v2/users", header(), request, response);

        assertThatCode(() -> logging.errorLog(httpApiCallResult)).doesNotThrowAnyException();
    }

    private Map<String, String> header() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer d6d64785492c26bf654f88db031777b7802821ca14e4ee62acd12acc29470d1f");
        return map;
    }
}
