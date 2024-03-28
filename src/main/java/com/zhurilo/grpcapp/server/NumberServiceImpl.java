package com.zhurilo.grpcapp.server;

import com.generated.grpc.NumberRequest;
import com.generated.grpc.NumberResponse;
import com.generated.grpc.NumberServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NumberServiceImpl extends NumberServiceGrpc.NumberServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(NumberServer.class);

    @Override
    public void numberSequence(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        int firstIdx = request.getFirstValue();
        int lastIdx = request.getLastValue();
        int period = 2;

        log.info("method getSequence() starts.");
        log.info("Number range: [{}, {}]", firstIdx, lastIdx);
        log.info("Sequence period: {} seconds", period);

        for (int idx = firstIdx + 1; idx <= lastIdx; idx++) {
            responseObserver.onNext(NumberResponse.newBuilder().setValue(idx).build());
            log.info("Generate ValueMessage. Iteration: {}", idx);
            try {
                TimeUnit.SECONDS.sleep(period);
            } catch (InterruptedException e) {
                log.info(e.getMessage(), e);
            }
        }
        responseObserver.onCompleted();
    }

}
