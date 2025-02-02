package com.oc.schoolapi.repository;

import com.oc.schoolapi.model.SchoolSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolSubjectRepository extends JpaRepository<SchoolSubject, Long> {
}
