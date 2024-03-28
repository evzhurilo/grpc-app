package com.zhurilo.grpcapp.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NumberServer {
    private static final Logger logger = LoggerFactory.getLogger(NumberServer.class);

    public static final int SERVER_PORT = 9000;
    private static final Logger log = LoggerFactory.getLogger(NumberServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("NumberServer is starting...");
        NumberServiceImpl service = new NumberServiceImpl();
        Server server = ServerBuilder.forPort(SERVER_PORT).addService(service).build();
        server.start();
        logger.info("Waiting for connections...");
        server.awaitTermination();

    }
}
