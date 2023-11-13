package org.example.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.proto.StudentRequest;
import org.example.proto.StudentResponse;
import org.example.proto.StudentServiceGrpc;

import java.util.concurrent.TimeUnit;

public class StudentService extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getStudent(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {

        final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;
        String serverAddress = "localhost:50052";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();

        try {
            blockingStub = StudentServiceGrpc.newBlockingStub(channel);
            StudentResponse response = blockingStub.getStudent(request);


            StudentResponse studentRes = StudentResponse
                    .newBuilder()
                    .setAge(response.getAge())
                    .setName(response.getName() == null ? "" : response.getName())
                    .build();
            // Send the response to the client.
            responseObserver.onNext(studentRes);
            // Notifies the customer that the call is completed.
            responseObserver.onCompleted();
        } finally {
            try {
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public StreamObserver<StudentRequest> sendAndGetStudentStream(StreamObserver<StudentResponse> responseObserver) {
        return new StreamObserver<>() {


            @Override
            public void onNext(StudentRequest studentRequest) {

            }

            @Override
            public void onError(Throwable throwable) {
// do nothing
            }

            @Override
            public void onCompleted() {
// do nothing
            }
        };
    }

}
