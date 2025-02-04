package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.SchoolSubjectDto;
import com.oc.schoolapi.model.SchoolSubject;

import java.util.Optional;
import java.util.Set;

public interface SchoolSubjectService {
    Set<SchoolSubject> getAll();
    Optional<SchoolSubject> get(Long id);
    Optional<SchoolSubject> create(SchoolSubjectDto schoolSubjectDto);
    Optional<SchoolSubject> update(SchoolSubjectDto schoolSubjectDto, SchoolSubject existingSchoolSubject);
    void delete(SchoolSubject schoolSubject);
}
