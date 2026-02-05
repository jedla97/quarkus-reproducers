package org.acme;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import org.acme.demo.EnumTriggerRequest;
import org.acme.demo.DemoEnum;
import org.acme.demo.DemoGrpc;
import org.acme.demo.EnumTriggerReply;

@QuarkusIntegrationTest
public class GreetingResourceIT {
    // Execute the same tests but in packaged mode.

    private ManagedChannel channel;

    @BeforeEach
    public void init() {
        channel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
    }

    @AfterEach
    public void cleanup() throws InterruptedException {
        channel.shutdown();
        channel.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testServerSideEnumLoggingInNativeMode() {
        EnumTriggerRequest request = EnumTriggerRequest.newBuilder()
                .setName("logging-test")
                .setEnum(DemoEnum.B)
                .build();
        EnumTriggerReply response = DemoGrpc.newBlockingStub(channel)
                .triggerEnumError(request);
    }
}
