package com.oc.schoolapi.repository;

import com.oc.schoolapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BaseUserRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<T> findUserByEmail(String email);
    Optional<T> findUserByFirstName(String firstName);
}
