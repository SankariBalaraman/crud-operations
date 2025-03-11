package com.example.crudoperations.dao;

import com.example.crudoperations.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User saveUser(User user);
    List<User> findAllUsers();
    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);
}

