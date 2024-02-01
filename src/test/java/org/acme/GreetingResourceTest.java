package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    @Disabled
    public void testHelloEndpoint() {
        given()
            .when().get("/test-http")
            .then()
            .statusCode(200)
            .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    @Disabled
    public void testHelloEndpoint2() {
        given()
                .when().get("/test-https")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    @Disabled
    public void testHelloEndpoint3() {
        given()
                .when().get("/test-http-quarkus")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    @Disabled
    public void testHelloEndpoint4() {
        given()
                .when().get("/test-https-quarkus")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    public void testHelloEndpoint5() {
        given()
                .when().get("/test-single")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

}