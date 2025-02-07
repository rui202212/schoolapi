package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.SchoolSubjectDto;
import com.oc.schoolapi.model.SchoolSubject;
import com.oc.schoolapi.service.SchoolSubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/schoolsubject")
public class SchoolSubjectController {
    private final SchoolSubjectService schoolSubjectService;
    private static final String NOT_FOUND_MESSAGE = "SchoolSubject not found.";


    public SchoolSubjectController(SchoolSubjectService schoolSubjectService) {
        this.schoolSubjectService = schoolSubjectService;
    }

    @GetMapping
    public ResponseEntity<Set<SchoolSubject>> getAll() {
        Set<SchoolSubject> schoolSubjects = this.schoolSubjectService.getAll();
        if (schoolSubjects.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No SchoolSubjects found.");
        }
        return ResponseEntity.ok(schoolSubjects);
    }

    @GetMapping("/{schoolsubjectId}")
    ResponseEntity<SchoolSubject> get(@PathVariable Long schoolsubjectId) {
        return ResponseEntity.ok(this.schoolSubjectService.get(schoolsubjectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @PostMapping
    ResponseEntity<SchoolSubject> create(@RequestBody SchoolSubjectDto schoolSubjectDto) {
        return this.schoolSubjectService.create(schoolSubjectDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{schoolsubjectId}")
    ResponseEntity<SchoolSubject> update(@RequestBody SchoolSubjectDto schoolSubjectDto, @PathVariable Long schoolsubjectId) {
        SchoolSubject existingSchoolSubject = this.schoolSubjectService.get(schoolsubjectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        return this.schoolSubjectService.update(schoolSubjectDto, existingSchoolSubject)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{schoolsubjectId}")
    ResponseEntity<Void> delete(@PathVariable Long schoolsubjectId) {
        SchoolSubject existingSchoolSubject = this.schoolSubjectService.get(schoolsubjectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.schoolSubjectService.delete(existingSchoolSubject);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
