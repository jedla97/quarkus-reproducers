package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

import java.io.IOException;
import java.util.List;

@Path("")
public class GreetingResource {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("dump")
    @Produces(MediaType.TEXT_PLAIN)
    public String dump() {
        final java.nio.file.Path dumpPath = java.nio.file.Path.of("./dump.jfr");
        try {
            try (Recording r = new Recording()) {
                r.start();
                r.stop();
                r.dump(dumpPath);
            } catch (Exception e) {
                throw new RuntimeException("JEDLA");
            }
            List<RecordedEvent> recordedEvents = RecordingFile.readAllEvents(dumpPath);
            return recordedEvents.toString();
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
