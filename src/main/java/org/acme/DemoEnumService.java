package org.acme;

import org.jboss.logging.Logger;

import io.quarkus.grpc.GrpcService;
import org.acme.demo.Demo;
import org.acme.demo.EnumTriggerReply;
import org.acme.demo.EnumTriggerRequest;
import io.smallrye.mutiny.Uni;

@GrpcService
public class DemoEnumService implements Demo {

    private static final Logger LOG = Logger.getLogger(DemoEnumService.class);

    @Override
    public Uni<EnumTriggerReply> triggerEnumError(EnumTriggerRequest request) {
        return Uni.createFrom().item(request)
                .invoke(r -> {
                    LOG.info("Received request: " + r);
                })
                .map(msg -> EnumTriggerReply.newBuilder()
                        .setName(msg.getName())
                        .setEnum(msg.getEnum())
                        .build());
    }
}
