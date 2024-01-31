package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
            .when().get("/test-http")
            .then()
            .statusCode(200)
            .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    public void testHelloEndpoint2() {
        given()
                .when().get("/test-https")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    public void testHelloEndpoint3() {
        given()
                .when().get("/test-https-quarkus")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

}