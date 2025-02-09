package com.oc.schoolapi.service;

import com.oc.schoolapi.model.User;
import com.oc.schoolapi.repository.BaseUserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public abstract class UserServiceImplement<T extends User, R extends BaseUserRepository<T>> implements UserService<T> {
    protected final R repository;

    protected UserServiceImplement(R repository) {
        this.repository = repository;
    }

    @Override
    public Set<T> getAll() {
        return new HashSet<>(this.repository.findAll());
    }

    @Override
    public Optional<T> get(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<T> getByUsername(String username) {
        return this.repository.findUserByEmail(username);
    }

    @Override
    public Optional<T> create(T user) {
        user.setCreatedDate(LocalDate.now());
        user.setUpdatedDate(LocalDate.now());
        return Optional.of(this.repository.save(user));
    }

    @Override
    public Optional<T> update(T user) {
        user.setUpdatedDate(LocalDate.now());
        return Optional.of(this.repository.save(user));
    }

    @Override
    public void delete(T user) {
        this.repository.delete(user);
    }
}
