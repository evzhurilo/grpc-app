package com.zhurilo.grpcapp.client;

import com.generated.grpc.NumberResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientStreamObserver implements StreamObserver<NumberResponse> {

    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);

    private int lastValue = 0;

    @Override
    public void onNext(NumberResponse value) {
        setLastValue(value.getValue());
        log.info("new value: {}", lastValue);
    }

    @Override
    public void onError(Throwable t) {
        log.error(t.getMessage(), t);
    }

    @Override
    public void onCompleted() {
        log.info("Server completed work");
    }

    public synchronized void setLastValue(int value) {
        lastValue = value;
    }

    public synchronized int getLastValueAndReset() {
        int result = lastValue;
        lastValue = 0;
        return result;
    }
}
