package org.example;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.proto.StudentRequest;
import org.example.proto.StudentResponse;
import org.example.proto.StudentServiceGrpc;

import java.util.concurrent.TimeUnit;

public class Main {

    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;

    public Main(Channel channel) {
        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {

        String serverAddress = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
        StudentRequest request = StudentRequest.newBuilder()
                .setId(100)
                .build();
        try {
            Main client = new Main(channel);
            StudentResponse response = client.blockingStub.getStudent(request);
            System.out.println("Age: " + response.getAge());
            System.out.println("Name: " + response.getName());

        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}