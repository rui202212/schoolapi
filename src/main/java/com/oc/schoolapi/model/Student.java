package com.oc.schoolapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Student Model
 */
@Entity
@Table(name = "student")
public class Student extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate birthDate;
    @ManyToMany(mappedBy = "students")
    private Set<SchoolClass> enrolledSchoolClasses = new HashSet<>();

    public Student() {}

    public Student(String firstName, String lastName, String email, String password, Set<UserType> roles, LocalDate createdDate, LocalDate updatedDate, Long id, LocalDate birthDate, Set<SchoolClass> enrolledSchoolClasses) {
        super(firstName, lastName, email, password, roles, createdDate, updatedDate);
        this.id = id;
        this.birthDate = birthDate;
        this.enrolledSchoolClasses = enrolledSchoolClasses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<SchoolClass> getEnrolledSchoolClasses() {
        return enrolledSchoolClasses;
    }

    public void setEnrolledSchoolClasses(Set<SchoolClass> enrolledSchoolClasses) {
        this.enrolledSchoolClasses = enrolledSchoolClasses;
    }
}
