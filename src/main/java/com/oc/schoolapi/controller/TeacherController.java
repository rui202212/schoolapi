package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.TeacherDto;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private static final String NOT_FOUND_MESSAGE = "Teacher not found.";

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<Set<Teacher>> getAll() {
        Set<Teacher> teachers = this.teacherService.getAll();
        if (teachers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No teachers found.");
        }
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> get(@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.teacherService.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @PostMapping
    ResponseEntity<Teacher> create(@RequestBody TeacherDto teacherDto) {
        return this.teacherService.create(teacherDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{teacherId}")
    ResponseEntity<Teacher> update(@RequestBody TeacherDto teacherDto, @PathVariable Long teacherId) {
        Teacher existingTeacher = this.teacherService.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        return this.teacherService.update(teacherDto, existingTeacher)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{teacherId}")
    ResponseEntity<Void> delete(@PathVariable Long teacherId) {
        Teacher existingTeacher = this.teacherService.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.teacherService.delete(existingTeacher);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
