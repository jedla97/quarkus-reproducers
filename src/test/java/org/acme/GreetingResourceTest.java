package org.acme;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.KeyStoreOptionsBase;
import io.vertx.core.net.OpenSSLEngineOptions;
import io.vertx.core.net.PfxOptions;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClientOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletionException;

import io.vertx.ext.web.client.WebClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class GreetingResourceTest {

    @TestHTTPResource(value = "/hello", tls = true)
    URL helloUrl;

    private static Vertx vertx;

    @BeforeAll
    static void setup() {
        vertx = Vertx.vertx();
    }

    @Test
    public void testX25519MLKEM768() {
        sendRequest(List.of("X25519MLKEM768"), helloUrl.toString());
    }

    @Test
    public void testSecP256r1MLKEM768() {
        sendRequestAndExpectFailure(List.of("SecP256r1MLKEM768"), helloUrl.toString());
    }

    public void sendRequest(List<String> keyExchangeGroup, String requestUrl) {
        var client = createWebClient(keyExchangeGroup);
        try {
            HttpResponse<Buffer> response = client.getAbs(requestUrl)
                    .send().toCompletionStage().toCompletableFuture().join();

            assertEquals(200, response.statusCode());
            assertEquals("Hello from RESTEasy Reactive", response.bodyAsString());
        } finally {
            client.close();
        }
    }

    public void sendRequestAndExpectFailure(List<String> keyExchangeGroup, String requestUrl) {

        var client = createWebClient(keyExchangeGroup);

        assertThrows(CompletionException.class, () -> {
            client.getAbs(requestUrl)
                    .send().toCompletionStage().toCompletableFuture().join();
        }, "Server must reject/fail handshake as client have " + keyExchangeGroup.toString()
                + " exchange groups as server not allow them");

        client.close();
    }

    public WebClient createWebClient(List<String> keyExchangeGroup) {
        WebClientOptions options = new WebClientOptions();
        options.setSsl(true);
        options.setSslEngineOptions(new OpenSSLEngineOptions());
        options.getSslOptions().setKeyExchangeGroups(keyExchangeGroup);
        options.setTrustAll(true);

        return WebClient.create(vertx, options);
    }

}