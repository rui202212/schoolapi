package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.StudentDto;
import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

/**
 * Student Controller
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Set<Student>> getAll(){
        // Get all students
        Set<Student> students =  this.studentService.getAll();
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found.");
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> get(@PathVariable Long studentId){
        // Get student by id from path variable and return it, if note found return http error not found 404
        return ResponseEntity.ok(this.studentService.get(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.")));
    }

    @PostMapping
    ResponseEntity<Student> create(@RequestBody StudentDto studentDto) {
        return this.studentService.create(studentDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{studentId}")
    ResponseEntity<Student> update(@RequestBody StudentDto studentDto, @PathVariable Long studentId) {
        // find existing student by id
        Student existingStudent = this.studentService.get(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return this.studentService.update(studentDto, existingStudent)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{studentId}")
    ResponseEntity<Void> delete(@PathVariable Long studentId) {
        Student existingStudent = this.studentService.get(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.studentService.delete(existingStudent);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
