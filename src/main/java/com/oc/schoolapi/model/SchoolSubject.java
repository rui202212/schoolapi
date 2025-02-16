package com.oc.schoolapi.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * SchoolSubject Model
 */
@Entity
@Table(name = "schoolsubject")
public class SchoolSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;
    private String description;
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "teacher_schoolsubject",
            joinColumns = @JoinColumn(name = "schoolsubject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> teachers = new HashSet<>();

    public SchoolSubject(){};

    public SchoolSubject(Long id, String subjectName, String description, Set<Teacher> teachers) {
        this.id = id;
        this.subjectName = subjectName;
        this.description = description;
        this.teachers = teachers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
