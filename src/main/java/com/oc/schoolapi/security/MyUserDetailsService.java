package com.oc.schoolapi.security;

import com.oc.schoolapi.model.SchoolAdmin;
import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.model.Teacher;
import com.oc.schoolapi.model.User;
import com.oc.schoolapi.service.SchoolAdminServiceImplement;
import com.oc.schoolapi.service.StudentServiceImplement;
import com.oc.schoolapi.service.TeacherServiceImplement;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
//    private final UserService userService;

    private final StudentServiceImplement studentServiceImplement;
    private final TeacherServiceImplement teacherServiceImplement;
    private final SchoolAdminServiceImplement schoolAdminServiceImplement;

//    public MyUserDetailsService(UserService userService) {
//        this.userService = userService;
//    }

    public MyUserDetailsService(
            StudentServiceImplement studentServiceImplement,
            TeacherServiceImplement teacherServiceImplement,
            SchoolAdminServiceImplement schoolAdminServiceImplement
    ) {
        this.studentServiceImplement = studentServiceImplement;
        this.teacherServiceImplement = teacherServiceImplement;
        this.schoolAdminServiceImplement = schoolAdminServiceImplement;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with email "+username+" not found.");
        }

        // -- Convert user roles to GrantedAuthority objects of spring security
        // -- This is required by spring security to check user roles in security config
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        // -- Return a new UserDetails object
        return new org.springframework.security.core.userdetails.User( // This is the building of spring boot PRINCIPAL USER
                user.getEmail(), // Email is our username
                user.getPassword(), // The password of our user
                authorities // The roles
        );

        /*User user = userService.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // RBAC => ROLE BASED ACCESS CONTROL
        // -- Convert user roles to GrantedAuthority objects of spring security
        // -- This is required by spring security to check user roles in security config
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        // -- Return a new UserDetails object
        return new org.springframework.security.core.userdetails.User( // This is the building of spring boot PRINCIPAL USER
                user.getEmail(), // Email is our username
                user.getPassword(), // The password of our user
                authorities //
        );*/
    }

    private User findUserByEmail(String username) {
        Optional<Student> student = studentServiceImplement.getByUsername(username);
        if (student.isPresent()) {
            return student.get();
        }

        Optional<Teacher> teacher = teacherServiceImplement.getByUsername(username);
        if (teacher.isPresent()) {
            return teacher.get();
        }

        Optional<SchoolAdmin> schoolAdmin = schoolAdminServiceImplement.getByUsername(username);
        if (schoolAdmin.isPresent()) {
            return schoolAdmin.get();
        }

        return null;
    }
}
