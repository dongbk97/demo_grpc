package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.entity.Student;
import org.example.proto.StudentRequest;
import org.example.proto.StudentResponse;
import org.example.proto.StudentServiceGrpc;

public class StudentService extends StudentServiceGrpc.StudentServiceImplBase{
    @Override
    public void getStudent(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        int id = request.getId();
        Student student = Student.getInstance(id);
        StudentResponse studentRes = StudentResponse
                .newBuilder()
                .setAge(student.getAge()+1)
                .setName(student.getName() == null ? "" : student.getName())
                .build();
        // Send the response to the client.
        responseObserver.onNext(studentRes);
        // Notifies the customer that the call is completed.
        responseObserver.onCompleted();
    }
}
