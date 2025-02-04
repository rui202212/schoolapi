package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.SchoolClassDto;
import com.oc.schoolapi.dto.mapper.SchoolClassMapper;
import com.oc.schoolapi.model.SchoolClass;
import com.oc.schoolapi.repository.SchoolClassRepository;
import com.oc.schoolapi.repository.SchoolSubjectRepository;
import com.oc.schoolapi.repository.StudentRepository;
import com.oc.schoolapi.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {
    private SchoolClassRepository schoolClassRepository;
    private SchoolSubjectRepository schoolSubjectRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;

    public SchoolClassServiceImpl(
            SchoolClassRepository schoolClassRepository,
            SchoolSubjectRepository schoolSubjectRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository
    ) {
        this.schoolClassRepository = schoolClassRepository;
        this.schoolSubjectRepository = schoolSubjectRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }
    @Override
    public Set<SchoolClass> getAll() {
        return new HashSet<>(this.schoolClassRepository.findAll());
    }

    @Override
    public Optional<SchoolClass> get(Long id) {
        return this.schoolClassRepository.findById(id);
    }

    @Override
    public Optional<SchoolClass> create(SchoolClassDto schoolClassDto) {
        SchoolClass schoolClassToAdd = SchoolClassMapper.toSchoolClass(schoolClassDto, schoolSubjectRepository, teacherRepository, studentRepository);
        return Optional.of(this.schoolClassRepository.save(schoolClassToAdd));
    }

    @Override
    public Optional<SchoolClass> update(SchoolClassDto schoolClassDto, SchoolClass existingSchoolClass) {
        SchoolClass schoolClassToModify = SchoolClassMapper.toSchoolClass(schoolClassDto, schoolSubjectRepository, teacherRepository, studentRepository);
        schoolClassToModify.setId(existingSchoolClass.getId());
        return Optional.of(this.schoolClassRepository.save(schoolClassToModify));
    }

    @Override
    public void delete(SchoolClass schoolClass) {
        this.schoolClassRepository.delete(schoolClass);
    }
}
