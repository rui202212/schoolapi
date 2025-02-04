package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.SchoolSubjectDto;
import com.oc.schoolapi.dto.mapper.SchoolSubjectMapper;
import com.oc.schoolapi.model.SchoolSubject;
import com.oc.schoolapi.repository.SchoolSubjectRepository;
import com.oc.schoolapi.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SchoolSubjectServiceImpl implements SchoolSubjectService {
    private SchoolSubjectRepository schoolSubjectRepository;
    private TeacherRepository teacherRepository;

    public SchoolSubjectServiceImpl(
            SchoolSubjectRepository schoolSubjectRepository,
            TeacherRepository teacherRepository
    ) {
        this.schoolSubjectRepository = schoolSubjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Set<SchoolSubject> getAll() {
        return new HashSet<>(this.schoolSubjectRepository.findAll());
    }

    @Override
    public Optional<SchoolSubject> get(Long id) {
        return this.schoolSubjectRepository.findById(id);
    }

    @Override
    public Optional<SchoolSubject> create(SchoolSubjectDto schoolSubjectDto) {
        SchoolSubject schoolSubjectToAdd = SchoolSubjectMapper.toSchoolSubject(schoolSubjectDto, teacherRepository);
        return Optional.of(this.schoolSubjectRepository.save(schoolSubjectToAdd));
    }

    @Override
    public Optional<SchoolSubject> update(SchoolSubjectDto schoolSubjectDto, SchoolSubject existingSchoolSubject) {
        SchoolSubject schoolSubjectToModify = SchoolSubjectMapper.toSchoolSubject(schoolSubjectDto, teacherRepository);
        schoolSubjectToModify.setId(existingSchoolSubject.getId());
        return Optional.of(this.schoolSubjectRepository.save(schoolSubjectToModify));
    }

    @Override
    public void delete(SchoolSubject schoolSubject) {
        this.schoolSubjectRepository.delete(schoolSubject);
    }
}
