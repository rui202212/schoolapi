package com.oc.schoolapi.repository;

import com.oc.schoolapi.model.Student;
import org.springframework.stereotype.Repository;

/**
 * Student Repository
 * Student : model of our table
 * Long: type of our primary key
 */
@Repository
public interface StudentRepository extends BaseUserRepository<Student> {
}
