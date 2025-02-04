package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.TeacherDto;
import com.oc.schoolapi.dto.mapper.TeacherMapper;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.model.UserType;
import com.oc.schoolapi.repository.SchoolClassRepository;
import com.oc.schoolapi.repository.SchoolSubjectRepository;
import com.oc.schoolapi.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;
    private SchoolSubjectRepository schoolSubjectRepository;
    private SchoolClassRepository schoolClassRepository;

    public TeacherServiceImpl(
            TeacherRepository teacherRepository,
            SchoolSubjectRepository schoolSubjectRepository,
            SchoolClassRepository schoolClassRepository
    ) {
        this.teacherRepository = teacherRepository;
        this.schoolSubjectRepository = schoolSubjectRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public Set<Teacher> getAll() {
        return new HashSet<>(this.teacherRepository.findAll());
    }

    @Override
    public Optional<Teacher> get(Long id) {
        return this.teacherRepository.findById(id);
    }

    @Override
    public Optional<Teacher> create(TeacherDto teacherDto) {
        Teacher teacherToAdd = TeacherMapper.toTeacher(teacherDto, schoolSubjectRepository, schoolClassRepository);
        teacherToAdd.setRoles(Set.of(UserType.TEACHER));
        teacherToAdd.setCreatedDate(LocalDate.now());
        teacherToAdd.setUpdatedDate(LocalDate.now());
        return Optional.of(this.teacherRepository.save(teacherToAdd));
    }

    @Override
    public Optional<Teacher> update(TeacherDto teacherDto, Teacher existingTeacher) {
        Teacher teacherToModify = TeacherMapper.toTeacher(teacherDto, schoolSubjectRepository, schoolClassRepository);
        if (teacherToModify.getRoles() == null || teacherToModify.getRoles().isEmpty()) {
            teacherToModify.setRoles(Set.of(UserType.TEACHER));
        } else if (! teacherToModify.getRoles().equals(Set.of(UserType.TEACHER))) {
            teacherToModify.getRoles().add(UserType.TEACHER);
        }
        teacherToModify.setId(existingTeacher.getId());
        teacherToModify.setCreatedDate(existingTeacher.getCreatedDate());
        teacherToModify.setUpdatedDate(LocalDate.now());
        return Optional.of(this.teacherRepository.save(teacherToModify));
    }

    @Override
    public void delete(Teacher teacher) {
        this.teacherRepository.delete(teacher);
    }
}
