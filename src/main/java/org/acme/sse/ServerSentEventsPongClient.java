package org.acme.sse;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface ServerSentEventsPongClient {
    @GET
    @Path("/server-sent-events-pong")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    Multi<String> getPong();

}
