# API 호출 모듈

API 호출시(현재는 Http Api 호출만 지원) 요청과 응답을 로깅 하는 모듈

## 사용법

```java
Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");

Response createdUser = httpApiCall.callApi(
    HttpRequest.of(HttpMethod.POST, "https://gorest.co.in/public/v2/users", header(), request, Response.class));
```

자세한 사용법은
[RestTemplateHttpApiCallTest](https://github.com/juniqlim/api-call/blob/master/src/test/java/io/github/juniqlim/apicall/http/RestTemplateHttpApiCallTest.java)
클래스 참조.