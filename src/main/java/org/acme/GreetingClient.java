package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

public interface GreetingClient {

    @GET
    @Path("/hello")
    Response getGreeting();

}
