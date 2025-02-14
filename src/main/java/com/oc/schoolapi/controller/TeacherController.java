package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.ApiErrorResponse;
import com.oc.schoolapi.dto.TeacherDto;
import com.oc.schoolapi.dto.mapper.TeacherMapper;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.repository.SchoolClassRepository;
import com.oc.schoolapi.repository.SchoolSubjectRepository;
import com.oc.schoolapi.service.TeacherServiceImplement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

/**
 * Teacher Controller
 */
@RestController
@Tag(name = "Teachers", description = "Endpoints to manage teachers.")
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

    @Operation(
            summary = "To get all teachers",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "404",
            description = "No teachers found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping
    public ResponseEntity<Set<Teacher>> getAll() {
        Set<Teacher> teachers = this.teacherServiceImplement.getAll();
        if (teachers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No teachers found.");
        }
        return ResponseEntity.ok(teachers);
    }

    @Operation(
            summary = "To get a teacher by ID",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful",
            content = {@Content(mediaType = "application/json")}
    )
    @ApiResponse(
            responseCode = "404",
            description = "Teacher not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> get(@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.teacherServiceImplement.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE)));
    }

    @Operation(
            summary = "To create a new entity of teacher",
            description = "The role ADMIN is required."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Teacher created successfully",
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
    ResponseEntity<Teacher> create(@RequestBody TeacherDto teacherDto) {
        Teacher teacherToAdd = TeacherMapper.toTeacher(teacherDto, schoolSubjectRepository, schoolClassRepository);
        teacherToAdd.setPassword(encoder.encode(teacherToAdd.getPassword()));

        return this.teacherServiceImplement.create(teacherToAdd)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create teacher."));
    }

    @Operation(
            summary = "To update a teacher",
            description = "Requires authentication."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Teacher updated successfully.",
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
            description = "Teacher not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
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

    @Operation(
            summary = "To delete a teacher",
            description = "The role ADMIN is required."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Teacher deleted successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Teacher not found."
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> delete(@PathVariable Long teacherId) {
        Teacher existingTeacher = this.teacherServiceImplement.get(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        this.teacherServiceImplement.delete(existingTeacher);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
