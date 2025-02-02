package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.SchoolClassDto;
import com.oc.schoolapi.model.SchoolClass;

import java.util.Optional;
import java.util.Set;

public interface SchoolClassService {
    Set<SchoolClass> getAll();
    Optional<SchoolClass> get(Long id);
    Optional<SchoolClass> create(SchoolClassDto schoolClassDto);
    Optional<SchoolClass> update(SchoolClassDto schoolClassDto);
    void delete(SchoolClass schoolClass);
}
