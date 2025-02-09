package com.oc.schoolapi.service;

import com.oc.schoolapi.model.User;

import java.util.Optional;
import java.util.Set;

/**
 * User service interface
 */
public interface UserService<T extends User> {
    /**
     * Get all users
     * @return the Set of all users
     */
    Set<T> getAll();

    /**
     * Get user by id
     * @param id the user id
     * @return the user find by id
     */
    Optional<T> get(Long id);

    /**
     * Get user by username (email)
     * @param username the user's email
     * @return the user find by email
     */
    Optional<T> getByUsername(String username);

    /**
     * Create a new user
     * @param user the user to create
     * @return the new user created
     */
    Optional<T> create(T user);

    /**
     * Update existing user
     * @param user the user to update
     * @return user updated
     */
    Optional<T> update(T user);

    /**
     * Delete existing user
     * @param user the user to delete
     */
    void delete(T user);
}
