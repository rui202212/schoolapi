package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.ApiErrorResponse;
import com.oc.schoolapi.dto.SchoolSubjectDto;
import com.oc.schoolapi.model.SchoolSubject;
import com.oc.schoolapi.service.SchoolSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

/**
 * Subject Controller
 */
@RestController
@Tag(name = "Subjects", description = "Endpoints to manage subjects.")
@RequestMapping("/schoolsubject")
public class SchoolSubjectController {
    private final SchoolSubjectService schoolSubjectService;
    private static final String NOT_FOUND_MESSAGE = "SchoolSubject not found.";


    public SchoolSubjectController(SchoolSubjectService schoolSubjectService) {
        this.schoolSubjectService = schoolSubjectService;
    }

    @Operation(
            summary = "To get all subjects",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "404",
            description = "No subjects found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Set<SchoolSubject>> getAll() {
        Set<SchoolSubject> schoolSubjects = this.schoolSubjectService.getAll();
        if (schoolSubjects.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No SchoolSubjects found.");
        }
        return ResponseEntity.ok(schoolSubjects);
    }

    @Operation(
            summary = "To get a subject by ID",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "404",
            description = "Subject not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{schoolsubjectId}")
    ResponseEntity<SchoolSubject> get(@PathVariable Long schoolsubjectId) {
        return ResponseEntity.ok(this.schoolSubjectService.get(schoolsubjectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @Operation(
            summary = "To create a new subject",
            description = "The role ADMIN is required."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Subject created successfully",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    ResponseEntity<SchoolSubject> create(@RequestBody SchoolSubjectDto schoolSubjectDto) {
        return this.schoolSubjectService.create(schoolSubjectDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(
            summary = "To update a subject",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Subject updated successfully.",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Subject not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{schoolsubjectId}")
    ResponseEntity<SchoolSubject> update(@RequestBody SchoolSubjectDto schoolSubjectDto, @PathVariable Long schoolsubjectId) {
        SchoolSubject existingSchoolSubject = this.schoolSubjectService.get(schoolsubjectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        return this.schoolSubjectService.update(schoolSubjectDto, existingSchoolSubject)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(
            summary = "To delete a subject",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Subject deleted successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Subject not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{schoolsubjectId}")
    ResponseEntity<Void> delete(@PathVariable Long schoolsubjectId) {
        SchoolSubject existingSchoolSubject = this.schoolSubjectService.get(schoolsubjectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.schoolSubjectService.delete(existingSchoolSubject);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
