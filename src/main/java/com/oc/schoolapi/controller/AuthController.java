package com.oc.schoolapi.controller;

import com.oc.schoolapi.dto.AuthenticationRequestCreateDto;
import com.oc.schoolapi.dto.AuthenticationRequestDto;
import com.oc.schoolapi.model.SchoolAdmin;
import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.model.User;
import com.oc.schoolapi.security.JwtUtil;
import com.oc.schoolapi.security.MyUserDetailsService;
import com.oc.schoolapi.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final MyUserDetailsService userDetailsService;

    private final StudentServiceImplement studentServiceImplement;
    private final TeacherServiceImplement teacherServiceImplement;
    private final SchoolAdminServiceImplement schoolAdminServiceImplement;

    private final PasswordEncoder encoder;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            MyUserDetailsService userDetailsService,
            StudentServiceImplement studentServiceImplement,
            TeacherServiceImplement teacherServiceImplement,
            SchoolAdminServiceImplement schoolAdminServiceImplement,
            PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.studentServiceImplement = studentServiceImplement;
        this.teacherServiceImplement = teacherServiceImplement;
        this.schoolAdminServiceImplement = schoolAdminServiceImplement;
        this.encoder = encoder;
    }

    @Operation(
            summary = "Endpoint for authentication"
    )
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDto.email(),
                            authenticationRequestDto.password()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDto.email());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("accessToken", jwt));
    }

    @Operation(
            summary = "Endpoint to creat account"
    )
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequestCreateDto authenticationRequestCreateDto) {
        
        // -- Check if the username already exists
        if (
                studentServiceImplement.getByUsername(authenticationRequestCreateDto.email()).isPresent() ||
                teacherServiceImplement.getByUsername(authenticationRequestCreateDto.email()).isPresent() ||
                schoolAdminServiceImplement.getByUsername(authenticationRequestCreateDto.email()).isPresent()
        ) {
            throw new RuntimeException("Error: user already exists, please sign-in.");
        }

        User newUser;

        // -- Create a new user's account based on user type
        switch (authenticationRequestCreateDto.userType()) {
            case STUDENT -> {
                Student student = new Student();
                student.setEmail(authenticationRequestCreateDto.email());
                student.setPassword(encoder.encode(authenticationRequestCreateDto.password()));
                student.setFirstName(authenticationRequestCreateDto.firstName());
                student.setLastName(authenticationRequestCreateDto.lastName());
                student.setRoles(new HashSet<>(authenticationRequestCreateDto.roles()));
                newUser = studentServiceImplement.create(student)
                        .orElseThrow(() -> new RuntimeException("Error occurred while adding user"));
            }
            case TEACHER -> {
                Teacher teacher = new Teacher();
                teacher.setEmail(authenticationRequestCreateDto.email());
                teacher.setPassword(encoder.encode(authenticationRequestCreateDto.password()));
                teacher.setFirstName(authenticationRequestCreateDto.firstName());
                teacher.setLastName(authenticationRequestCreateDto.lastName());
                teacher.setRoles(new HashSet<>(authenticationRequestCreateDto.roles()));
                newUser = teacherServiceImplement.create(teacher)
                        .orElseThrow(() -> new RuntimeException("Error occurred while adding user"));
            }
            case ADMIN -> {
                SchoolAdmin admin = new SchoolAdmin();
                admin.setEmail(authenticationRequestCreateDto.email());
                admin.setPassword(encoder.encode(authenticationRequestCreateDto.password()));
                admin.setFirstName(authenticationRequestCreateDto.firstName());
                admin.setLastName(authenticationRequestCreateDto.lastName());
                admin.setRoles(new HashSet<>(authenticationRequestCreateDto.roles()));
                newUser = schoolAdminServiceImplement.create(admin)
                        .orElseThrow(() -> new RuntimeException("Error occurred while adding user"));
            }
            default ->
                throw new IllegalArgumentException("Invalid user type. Allowed values: STUDENT, TEACHER, ADMIN.");
                    // throw new IllegalStateException("Unexpected value: " + authenticationRequestCreateDto.userType());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                "message", "User successfully created",
                "email", newUser.getEmail())
                );
    }
}