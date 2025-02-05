package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.SchoolClassDto;
import com.oc.schoolapi.model.SchoolClass;
import com.oc.schoolapi.service.SchoolClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/schoolclasses")
public class SchoolClassController {
    private final SchoolClassService schoolClassService;
    private static final String NOT_FOUND_MESSAGE = "SchoolClass not found.";

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    public ResponseEntity<Set<SchoolClass>> getAll() {
        Set<SchoolClass> schoolClasses = this.schoolClassService.getAll();
        if (schoolClasses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No SchoolClasses found.");
        }
        return ResponseEntity.ok(schoolClasses);
    }

    @GetMapping("/{schoolclassId}")
    public ResponseEntity<SchoolClass> get(@PathVariable Long schoolclassId) {
        return ResponseEntity.ok(this.schoolClassService.get(schoolclassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @PostMapping
    ResponseEntity<SchoolClass> create(@RequestBody SchoolClassDto schoolClassDto) {
        return this.schoolClassService.create(schoolClassDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{schoolclassId}")
    ResponseEntity<SchoolClass> update(@RequestBody SchoolClassDto schoolClassDto, @PathVariable Long schoolclassId) {
        SchoolClass existingSchoolClass = this.schoolClassService.get(schoolclassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        return this.schoolClassService.update(schoolClassDto, existingSchoolClass)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{schoolclassId}")
    ResponseEntity<Void> delete(@PathVariable Long schoolclassId) {
        SchoolClass existingSchoolClass = this.schoolClassService.get(schoolclassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.schoolClassService.delete(existingSchoolClass);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
