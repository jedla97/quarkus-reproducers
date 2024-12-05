package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.vertx.core.http.HttpHeaders;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    void testHelloEndpoint() {
        Response response = given()
                .when().get("/index.html")
                .then().extract().response();

        String lastModified = response.header(HttpHeaders.LAST_MODIFIED.toString());
        assertNotNull(lastModified);
    }

}