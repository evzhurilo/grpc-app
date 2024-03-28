package com.zhurilo.grpcapp.client;

import com.generated.grpc.NumberRequest;
import com.generated.grpc.NumberServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AppClient {

    private static final Logger log = LoggerFactory.getLogger(AppClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9000;


    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        log.info("Channel created...");

        NumberServiceGrpc.NumberServiceStub stub = NumberServiceGrpc.newStub(channel);

        int firstValue = 0;
        int lastValue = 30;
        NumberRequest numberRequest = NumberRequest.newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();
        log.info("Number request created...");

        int currentValue = 0;

        ClientStreamObserver responseObserver = new ClientStreamObserver();
        stub.numberSequence(numberRequest, responseObserver);

        for (int i = 0; i < 50; i++) {
            currentValue = currentValue + responseObserver.getLastValueAndReset() + 1;
            log.info("currentValue: {}", currentValue);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        channel.shutdown();
        log.info("Client completed work");
    }
}
