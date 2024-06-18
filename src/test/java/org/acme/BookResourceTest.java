package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void ensureFieldPageableIsSerialized() {
        Response response =  given()
                .accept("application/json")
                .queryParam("size", "2")
                .queryParam("page", "1")
                .when().get("/book/paged");
        Assertions.assertEquals(200, response.statusCode());
        String pageable = response.body().jsonPath().get("pageable");
        Assertions.assertNotNull(pageable,
                "Field 'pageable' of org.springframework.data.domain.PageImpl was not serialized");
    }

}