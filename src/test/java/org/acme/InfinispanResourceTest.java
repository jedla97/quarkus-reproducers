package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class InfinispanResourceTest {

    @BeforeEach
    public void setup() {
        given()
                .delete("/books/clear")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .post("/books/commit")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}