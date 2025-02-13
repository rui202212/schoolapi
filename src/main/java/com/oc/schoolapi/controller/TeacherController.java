package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.TeacherDto;
import com.oc.schoolapi.dto.mapper.TeacherMapper;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.repository.SchoolClassRepository;
import com.oc.schoolapi.repository.SchoolSubjectRepository;
import com.oc.schoolapi.service.TeacherServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherServiceImplement teacherServiceImplement;
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final PasswordEncoder encoder;
    private static final String NOT_FOUND_MESSAGE = "Teacher not found.";

    public TeacherController(
            TeacherServiceImplement teacherServiceImplement,
            SchoolSubjectRepository schoolSubjectRepository,
            SchoolClassRepository schoolClassRepository,
            PasswordEncoder encoder
    ) {
        this.teacherServiceImplement = teacherServiceImplement;
        this.schoolSubjectRepository = schoolSubjectRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity<Set<Teacher>> getAll() {
        Set<Teacher> teachers = this.teacherServiceImplement.getAll();
        if (teachers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No teachers found.");
        }
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> get(@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.teacherServiceImplement.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @PostMapping
    ResponseEntity<Teacher> create(@RequestBody TeacherDto teacherDto) {
        Teacher teacherToAdd = TeacherMapper.toTeacher(teacherDto, schoolSubjectRepository, schoolClassRepository);
        teacherToAdd.setPassword(encoder.encode(teacherToAdd.getPassword()));

        return this.teacherServiceImplement.create(teacherToAdd)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create teacher."));
    }

    @PutMapping("/{teacherId}")
    ResponseEntity<Teacher> update(@RequestBody TeacherDto teacherDto, @PathVariable Long teacherId) {
        Teacher existingTeacher = this.teacherServiceImplement.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));

        Teacher teacherToUpdate = TeacherMapper.toTeacher(teacherDto, schoolSubjectRepository, schoolClassRepository);
        teacherToUpdate.setId(existingTeacher.getId());
        teacherToUpdate.setPassword(existingTeacher.getPassword());

        return this.teacherServiceImplement.update(teacherToUpdate)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update teacher."));
    }

    @DeleteMapping("/{teacherId}")
    ResponseEntity<Void> delete(@PathVariable Long teacherId) {
        Teacher existingTeacher = this.teacherServiceImplement.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.teacherServiceImplement.delete(existingTeacher);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
