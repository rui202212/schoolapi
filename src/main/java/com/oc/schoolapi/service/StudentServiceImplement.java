package com.oc.schoolapi.service;

import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplement extends UserServiceImplement<Student, StudentRepository> {
    protected StudentServiceImplement(StudentRepository repository) {
        super(repository);
    }
}
