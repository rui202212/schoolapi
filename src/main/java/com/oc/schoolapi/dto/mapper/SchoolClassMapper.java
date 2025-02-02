package com.oc.schoolapi.dto.mapper;

import com.oc.schoolapi.dto.SchoolClassDto;
import com.oc.schoolapi.model.SchoolClass;
import com.oc.schoolapi.model.SchoolSubject;
import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.repository.SchoolSubjectRepository;
import com.oc.schoolapi.repository.StudentRepository;
import com.oc.schoolapi.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

public class SchoolClassMapper {
    public static SchoolClass toSchoolClass(
            SchoolClassDto schoolClassDto,
            SchoolSubjectRepository schoolSubjectRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository
            ) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setClassName(schoolClassDto.className());

        if (schoolClassDto.schoolSubjectId()!=null && schoolClassDto.schoolSubjectId()!=0) {
            SchoolSubject schoolSubject = schoolSubjectRepository.findById(schoolClassDto.schoolSubjectId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SchoolSubject not found."));
            schoolClass.setSchoolSubject(schoolSubject);
        }

        if (schoolClassDto.teacherId()!=null && schoolClassDto.teacherId()!=0) {
            Teacher teacher = teacherRepository.findById(schoolClassDto.teacherId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found."));
            schoolClass.setTeacher(teacher);
        }

        if (schoolClassDto.studentsIds()!=null && !schoolClassDto.studentsIds().isEmpty()){
            Set<Student> students = new HashSet<>();
            for (Long studentId : schoolClassDto.studentsIds()) {
                Student student = studentRepository.findById(studentId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student of ID="+studentId+" not found."));
                students.add(student);
            }
            schoolClass.setStudents(students);
        }

        return schoolClass;
    }
}
