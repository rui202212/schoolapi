package com.oc.schoolapi.service;

import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImplement extends UserServiceImplement<Teacher, TeacherRepository> {
    protected TeacherServiceImplement(TeacherRepository repository) {
        super(repository);
    }
}
