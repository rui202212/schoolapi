package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.ApiErrorResponse;
import com.oc.schoolapi.dto.SchoolClassDto;
import com.oc.schoolapi.model.SchoolClass;
import com.oc.schoolapi.service.SchoolClassService;
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
 * Class Controller
 */
@RestController
@Tag(name = "Classes", description = "Endpoints to manage classes.")
@RequestMapping("/schoolclasses")
public class SchoolClassController {
    private final SchoolClassService schoolClassService;
    private static final String NOT_FOUND_MESSAGE = "SchoolClass not found.";

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @Operation(
            summary = "To get all classes",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "404",
            description = "No classes found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping
    public ResponseEntity<Set<SchoolClass>> getAll() {
        Set<SchoolClass> schoolClasses = this.schoolClassService.getAll();
        if (schoolClasses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No SchoolClasses found.");
        }
        return ResponseEntity.ok(schoolClasses);
    }

    @Operation(
            summary = "To get a class by ID",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "404",
            description = "Class not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/{schoolclassId}")
    public ResponseEntity<SchoolClass> get(@PathVariable Long schoolclassId) {
        return ResponseEntity.ok(this.schoolClassService.get(schoolclassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @Operation(
            summary = "To create a new class",
            description = "The role ADMIN is required."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Class created successfully",
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
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<SchoolClass> create(@RequestBody SchoolClassDto schoolClassDto) {
        return this.schoolClassService.create(schoolClassDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(
            summary = "To update a class",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Class updated successfully.",
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
            description = "Class not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PutMapping("/{schoolclassId}")
    ResponseEntity<SchoolClass> update(@RequestBody SchoolClassDto schoolClassDto, @PathVariable Long schoolclassId) {
        SchoolClass existingSchoolClass = this.schoolClassService.get(schoolclassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        return this.schoolClassService.update(schoolClassDto, existingSchoolClass)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(
            summary = "To delete a class",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Class deleted successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Class not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @DeleteMapping("/{schoolclassId}")
    ResponseEntity<Void> delete(@PathVariable Long schoolclassId) {
        SchoolClass existingSchoolClass = this.schoolClassService.get(schoolclassId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.schoolClassService.delete(existingSchoolClass);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
