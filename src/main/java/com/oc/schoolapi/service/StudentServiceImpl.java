package com.oc.schoolapi.service;

import com.oc.schoolapi.dto.StudentDto;
import com.oc.schoolapi.dto.mapper.StudentMapper;
import com.oc.schoolapi.model.Student;
import com.oc.schoolapi.model.UserType;
import com.oc.schoolapi.repository.SchoolClassRepository;
import com.oc.schoolapi.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentServiceImpl implements StudentService{
    private StudentRepository studentRepository;
    private SchoolClassRepository schoolClassRepository;

    public StudentServiceImpl(StudentRepository studentRepository, SchoolClassRepository schoolClassRepository) {
        this.studentRepository = studentRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public Set<Student> getAll() {
        return StreamSupport.stream(this.studentRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public Optional<Student> get(Long id) {
        return this.studentRepository.findById(id);
    }

    @Override
    public Optional<Student> create(StudentDto studentDto) {
        // Mapping studentDto to student to create
        Student studentToAdd = StudentMapper.toStudent(studentDto, schoolClassRepository);
        studentToAdd.setRoles(Set.of(UserType.STUDENT));
        studentToAdd.setCreatedDate(LocalDate.now());
        studentToAdd.setUpdatedDate(LocalDate.now());
        return Optional.of(this.studentRepository.save(studentToAdd));
    }

    @Override
    public Optional<Student> update(StudentDto studentDto, Student existingStudent) {
        Student studentToModify = StudentMapper.toStudent(studentDto, schoolClassRepository);
        if (studentToModify.getRoles() == null || studentToModify.getRoles().isEmpty()) {
            studentToModify.setRoles(Set.of(UserType.STUDENT));
        } else if (! studentToModify.getRoles().equals(Set.of(UserType.STUDENT))) {
            studentToModify.getRoles().add(UserType.STUDENT);
        }
        studentToModify.setId(existingStudent.getId());
        studentToModify.setCreatedDate(existingStudent.getCreatedDate());
        studentToModify.setUpdatedDate(LocalDate.now());
        return Optional.of(this.studentRepository.save(studentToModify));
    }

    @Override
    public void delete(Student student) {
        this.studentRepository.delete(student);
    }
}
