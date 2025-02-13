package com.oc.schoolapi.dto.mapper;

import com.oc.schoolapi.dto.TeacherDto;
import com.oc.schoolapi.model.SchoolClass;
import com.oc.schoolapi.model.SchoolSubject;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.repository.SchoolClassRepository;
import com.oc.schoolapi.repository.SchoolSubjectRepository;

import java.util.HashSet;
import java.util.Set;

public class TeacherMapper {
    private TeacherMapper() {
    }

    public static Teacher toTeacher(
            TeacherDto teacherDto,
            SchoolSubjectRepository schoolSubjectRepository,
            SchoolClassRepository schoolClassRepository
    ) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.firstName());
        teacher.setLastName(teacherDto.lastName());
        teacher.setEmail(teacherDto.email());
        teacher.setPassword(teacherDto.password());
        teacher.setRoles(new HashSet<>(teacherDto.roles()));

        if (teacherDto.subjectsTaughtIds()!=null && !teacherDto.subjectsTaughtIds().isEmpty()) {
            Set<SchoolSubject> schoolSubjects = new HashSet<>(schoolSubjectRepository.findAllById(teacherDto.subjectsTaughtIds()));
//            for (Long subjectId : teacherDto.subjectsTaughtIds()) {
//                schoolSubjectRepository.findById(subjectId).ifPresent(schoolSubjects::add);
//            }
            teacher.setSubjectsTaught(schoolSubjects);
        }

        if (teacherDto.assignedSchoolClassesIds()!=null && !teacherDto.assignedSchoolClassesIds().isEmpty()) {
            Set<SchoolClass> schoolClasses = new HashSet<>(schoolClassRepository.findAllById(teacherDto.assignedSchoolClassesIds()));
//            for (Long classId : teacherDto.assignedSchoolClassesIds()) {
//                schoolClassRepository.findById(classId).ifPresent(schoolClasses::add);
//            }
            teacher.setAssignedSchoolClasses(schoolClasses);
        }

        return teacher;
    }
}
