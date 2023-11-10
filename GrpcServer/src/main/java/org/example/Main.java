package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.service.StudentService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    private Server server;
    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port).addService(new StudentService()).build().start();



        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("Shutting down gRPC server");
                try {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }
    public static void main(String[] args) throws IOException, InterruptedException {

        final Main main = new Main();
        main.start();
        main.server.awaitTermination();
    }
}