package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class FunqyTest {

    @Test
    public void testDefaultChainGet() {
        RestAssured.given().contentType("application/json")
                .header("ce-specversion", "1.0")
                .header("ce-id", UUID.randomUUID().toString())
                .header("ce-type", "defaultChain")
                .header("ce-source", "test")
                .body("\"Start\"")
                .get()
                .then().statusCode(200)
                .header("ce-id", notNullValue())
                .header("ce-type", "defaultChain.output")
                .header("ce-source", "defaultChain")
                .body(Matchers.equalTo("\"Start::defaultChain\""));
    }

    @Test
    public void testDefaultChainPost() {
        RestAssured.given().contentType("application/json")
                .header("ce-specversion", "1.0")
                .header("ce-id", UUID.randomUUID().toString())
                .header("ce-type", "defaultChain")
                .header("ce-source", "test")
                .body("\"Start\"")
                .post()
                .then().statusCode(200)
                .header("ce-id", notNullValue())
                .header("ce-type", "defaultChain.output")
                .header("ce-source", "defaultChain")
                .body(Matchers.equalTo("\"Start::defaultChain\""));
    }
}
