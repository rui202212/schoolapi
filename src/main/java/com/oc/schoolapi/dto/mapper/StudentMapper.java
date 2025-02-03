package com.oc.schoolapi.dto.mapper;

import com.oc.schoolapi.dto.StudentDto;
import com.oc.schoolapi.model.SchoolClass;
import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.repository.SchoolClassRepository;

import java.util.HashSet;
import java.util.Set;

public class StudentMapper {
    private StudentMapper() {
    }

    public static Student toStudent(StudentDto studentDto, SchoolClassRepository schoolClassRepository){
        Student student = new Student();
        student.setFirstName(studentDto.firstName());
        student.setLastName(studentDto.lastName());
        student.setEmail(studentDto.email());
        student.setPassword(studentDto.password());
        student.setBirthDate(studentDto.birthDate());

        if (studentDto.enrolledSchoolClassesIds()!=null && !studentDto.enrolledSchoolClassesIds().isEmpty()) {
            Set<SchoolClass> enrolledSchoolClasses = new HashSet<>();
            for (Long classId : studentDto.enrolledSchoolClassesIds()) {
                schoolClassRepository.findById(classId).ifPresent(enrolledSchoolClasses::add);
            }
            student.setEnrolledSchoolClasses(enrolledSchoolClasses);
        }

        return student;
    }
}
