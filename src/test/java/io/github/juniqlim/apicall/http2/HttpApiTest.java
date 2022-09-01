package io.github.juniqlim.apicall.http2;

import io.github.juniqlim.apicall.http2.HttpApi.DefaultHttpApi;
import io.github.juniqlim.apicall.http2.HttpRequest.Smart;
import org.junit.jupiter.api.Test;

class HttpApiTest {
    @Test
    void test() {
        new DefaultHttpApi.Smart().to(new Smart().to("https://gorest.co.in/public/v2/users")).call();
    }
}
