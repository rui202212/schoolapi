package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.TeacherDto;
import com.oc.schoolapi.model.Teacher;

import java.util.Optional;
import java.util.Set;

public interface TeacherService {
    Set<Teacher> getAll();
    Optional<Teacher> get(Long id);
    Optional<Teacher> create(TeacherDto teacherDto);
    Optional<Teacher> update(TeacherDto teacherDto, Teacher existingTeacher);
    void delete(Teacher teacher);

}
