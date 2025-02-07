package com.oc.schoolapi.service;

import java.util.Optional;

/**
 * User service
 */
public interface UserService {
    /**
     * Get user by id
     * @param id the user id
     * @return the user find by id
     */
    Optional<User> get(Long id);

    /**
     * Get user by username (email)
     * @param username the user's email
     * @return the user find by email
     */
    Optional<User> getByUsername(String username);

    /**
     * Create a new user
     * @param user the user to create
     * @return the new user created
     */
    Optional<User> create(User user);

    /**
     * Update existing user
     * @param user the user to update
     * @return user updated
     */
    Optional<User> update(User user);

    /**
     * Delete existing user
     * @param user the user to delete
     */
    void delete(User user);
}
