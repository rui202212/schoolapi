package com.oc.schoolapi.dto.mapper;

import com.oc.schoolapi.dto.SchoolSubjectDto;
import com.oc.schoolapi.model.SchoolSubject;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.repository.TeacherRepository;

import java.util.HashSet;
import java.util.Set;

public class SchoolSubjectMapper {
    private SchoolSubjectMapper() {
    }

    public static SchoolSubject toSchoolSubject(
            SchoolSubjectDto schoolSubjectDto,
            TeacherRepository teacherRepository
    ) {
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setSubjectName(schoolSubjectDto.subjectName());
        schoolSubject.setDescription(schoolSubjectDto.description());

        if (schoolSubjectDto.teachersIds()!=null && !schoolSubjectDto.teachersIds().isEmpty()) {
            Set<Teacher> teachers = new HashSet<>();
            for (Long teacherId : schoolSubjectDto.teachersIds()) {
                teacherRepository.findById(teacherId).ifPresent(teachers::add);
            }
            schoolSubject.setTeachers(teachers);
        }

        return schoolSubject;
    }
}
