package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.StudentDto;
import com.oc.schoolapi.model.Student;

import java.util.Optional;
import java.util.Set;

/**
 * Student Service Interface
 */
public interface StudentService {
    /**
     * Get all students
     * @return the Set of all students
     */
    Set<Student> getAll();

    /**
     * Get a student by his id
     * @param id the student id
     * @return the student find by id
     */
    Optional<Student> get(Long id);

    /**
     * Create a new student
     *
     * @param studentDto the student to create
     * @return the new student created
     */
    Optional<Student> create(StudentDto studentDto);

    /**
     * Update existing student
     * @param studentDto the student to update
     * @return the student updated
     */
    Optional<Student> update(StudentDto studentDto, Student existingStudent);

    /**
     * Delete existing student
     * @param student the student to delete
     */
    void delete(Student student);

}
