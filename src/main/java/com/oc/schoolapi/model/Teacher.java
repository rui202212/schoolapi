package com.oc.schoolapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Teacher Model
 */
@Entity
@Table(name = "teacher")
public class Teacher extends User {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    @ManyToMany(
            mappedBy = "teachers",
            fetch = FetchType.EAGER
    )
    private Set<SchoolSubject> subjectsTaught = new HashSet<>();
    @OneToMany(
            mappedBy = "teacher",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<SchoolClass> assignedSchoolClasses = new HashSet<>();

    public Teacher() {}

    public Teacher(String firstName, String lastName, String email, String password, Set<UserType> roles, LocalDate createdDate, LocalDate updatedDate, /*Long id, */Set<SchoolSubject> subjectsTaught, Set<SchoolClass> assignedSchoolClasses) {
        super(firstName, lastName, email, password, roles, createdDate, updatedDate);
        /*this.id = id;*/
        this.subjectsTaught = subjectsTaught;
        this.assignedSchoolClasses = assignedSchoolClasses;
    }

    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public Set<SchoolSubject> getSubjectsTaught() {
        return subjectsTaught;
    }

    public void setSubjectsTaught(Set<SchoolSubject> subjectsTaught) {
        this.subjectsTaught = subjectsTaught;
    }

    public Set<SchoolClass> getAssignedSchoolClasses() {
        return assignedSchoolClasses;
    }

    public void setAssignedSchoolClasses(Set<SchoolClass> assignedSchoolClasses) {
        this.assignedSchoolClasses = assignedSchoolClasses;
    }
}
