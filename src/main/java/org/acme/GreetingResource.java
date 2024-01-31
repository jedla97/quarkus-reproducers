package org.acme;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpVersion;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.reactive.client.impl.ClientResponseImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Path("")
public class GreetingResource {

    @GET
    @Path("test-http")
    public Response httpClient() throws MalformedURLException {
        GreetingClient httpVersionClient = RestClientBuilder.newBuilder()
                .baseUrl(URI.create("http://localhost:8080").toURL())
                .build(GreetingClient.class);

        Response response = httpVersionClient.getGreeting();
        String httpVersion = ((ClientResponseImpl) response).getHttpVersion();
        if (httpVersion.equals("HTTP_2")) {
            return response;
        }

        return Response.ok("The HTTP version should be HTTP_2 but is " + httpVersion).build();
    }

    @GET
    @Path("test-https")
    public Response httpsClient() throws MalformedURLException {
        GreetingClient httpVersionClient = RestClientBuilder.newBuilder()
                .baseUrl(URI.create("https://localhost:8443").toURL())
                .build(GreetingClient.class);

        Response response = httpVersionClient.getGreeting();
        String httpVersion = ((ClientResponseImpl) response).getHttpVersion();
        if (httpVersion.equals("HTTP_2")) {
            return response;
        }

        return Response.ok("The HTTP version should be HTTP_2 but is " + httpVersion).build();
    }

    @GET
    @Path("test-http-quarkus")
    public Response quarkusHttpClient() throws MalformedURLException {
        List<HttpVersion> versions = Arrays.asList(HttpVersion.HTTP_2, HttpVersion.HTTP_1_1);
        HttpClientOptions options = new HttpClientOptions().setUseAlpn(true).setAlpnVersions(versions);
        GreetingClient httpVersionClient = QuarkusRestClientBuilder.newBuilder()
                .baseUrl(URI.create("http://localhost:8080").toURL()).httpClientOptions(options)
                .build(GreetingClient.class);

        Response response = httpVersionClient.getGreeting();
        String httpVersion = ((ClientResponseImpl) response).getHttpVersion();
        if (httpVersion.equals("HTTP_2")) {
            return response;
        }

        return Response.ok("The HTTP version should be HTTP_2 but is " + httpVersion).build();
    }

    @GET
    @Path("test-https-quarkus")
    public Response quarkusHttpsClient() throws MalformedURLException {
        List<HttpVersion> versions = Arrays.asList(HttpVersion.HTTP_1_1, HttpVersion.HTTP_2);
        HttpClientOptions options = new HttpClientOptions().setUseAlpn(true).setAlpnVersions(versions);
        GreetingClient httpVersionClient = QuarkusRestClientBuilder.newBuilder()
                .baseUrl(URI.create("https://localhost:8443").toURL()).httpClientOptions(options)
                .build(GreetingClient.class);

        Response response = httpVersionClient.getGreeting();
        String httpVersion = ((ClientResponseImpl) response).getHttpVersion();
        if (httpVersion.equals("HTTP_2")) {
            return response;
        }

        return Response.ok("The HTTP version should be HTTP_2 but is " + httpVersion).build();
    }


    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        return Response.ok("Hello from RESTEasy Reactive").build();
    }
}
