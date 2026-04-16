package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.acme.serialized.ShopItem;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class InfinispanClientTest {

    private final List<ShopItem> maxThresholdItemList = Arrays.asList(
            new ShopItem("Item 1", 100, ShopItem.Type.ELECTRONIC),
            new ShopItem("Item 2", 200, ShopItem.Type.ELECTRONIC));

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/items/clear-cache")
                .then()
                .statusCode(204);

        ShopItem additionalItem = new ShopItem("Item 3", 600, ShopItem.Type.MECHANICAL);
        whenAddCacheItems(maxThresholdItemList);
        whenAddCacheItems(List.of(additionalItem));

        Response response = given().get("/items");
        response.then().body("size()", is(2));

        response.then().body(containsString("Item 3"));
    }

    private void whenAddCacheItems(List<ShopItem> items) {
        items.forEach(item -> given()
                .header("Content-Type", "application/json")
                .body(item).when()
                .post("/items")
                .then().statusCode(HttpStatus.SC_OK));
    }
}
