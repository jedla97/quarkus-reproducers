package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "test-api")
public interface GreetingClient2 {

    @GET
    @Path("/hello")
    Response getGreeting();

}
