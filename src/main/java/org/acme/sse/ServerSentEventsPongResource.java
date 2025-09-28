package org.acme.sse;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.tracable.TraceableResource;

@Path("/server-sent-events-pong")
public class ServerSentEventsPongResource extends TraceableResource {

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> getPong() {
        recordTraceId();
        return Multi.createFrom().item("pong");
    }
}
