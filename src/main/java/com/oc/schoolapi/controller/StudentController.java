package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.StudentDto;
import com.oc.schoolapi.dto.mapper.StudentMapper;
import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.repository.SchoolClassRepository;
import com.oc.schoolapi.service.StudentServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

/**
 * Student Controller
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImplement studentServiceImplement;
    private final SchoolClassRepository schoolClassRepository;
    private final PasswordEncoder encoder;
    private static final String NOT_FOUND_MESSAGE = "Student not found.";

    public StudentController(StudentServiceImplement studentServiceImplement, SchoolClassRepository schoolClassRepository, PasswordEncoder encoder) {
        this.studentServiceImplement = studentServiceImplement;
        this.schoolClassRepository = schoolClassRepository;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity<Set<Student>> getAll(){
        // Get all students
        Set<Student> students =  this.studentServiceImplement.getAll();
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found.");
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> get(@PathVariable Long studentId){
        // Get student by id from path variable and return it, if note found return http error not found 404
        return ResponseEntity.ok(this.studentServiceImplement.get(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @PostMapping
    ResponseEntity<Student> create(@RequestBody StudentDto studentDto) {
        Student studentToAdd = StudentMapper.toStudent(studentDto, schoolClassRepository);
        studentToAdd.setPassword(encoder.encode(studentToAdd.getPassword()));

        return this.studentServiceImplement.create(studentToAdd)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create student."));
    }

    @PutMapping("/{studentId}")
    ResponseEntity<Student> update(@RequestBody StudentDto studentDto, @PathVariable Long studentId) {
        // find existing student by id
        Student existingStudent = this.studentServiceImplement.get(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));

        Student studentToUpdate = StudentMapper.toStudent(studentDto, schoolClassRepository);
        studentToUpdate.setId(existingStudent.getId());
        studentToUpdate.setPassword(existingStudent.getPassword());

        return this.studentServiceImplement.update(studentToUpdate)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update student."));
    }

    @DeleteMapping("/{studentId}")
    ResponseEntity<Void> delete(@PathVariable Long studentId) {
        Student existingStudent = this.studentServiceImplement.get(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.studentServiceImplement.delete(existingStudent);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
