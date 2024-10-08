package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jakarta.json.Json;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void error() {
        final String query = createQuery("error");
        final Response response = sendQuery(query);
        final JsonPath json = response.jsonPath();
        Assertions.assertEquals("42", json.getString("errors[0].extensions.code"));
    }

    public static Response sendQuery(String query) {
        return given().basePath("graphql")
                .contentType("application/json")
                .body(query)
                .post();
    }

    public static String createQuery(String query) {
        return Json.createObjectBuilder()
                .add("query", "{" + query + "}")
                .build().toString();
    }

}