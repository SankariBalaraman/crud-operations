package com.example.crudoperations.service;

import com.example.crudoperations.dao.UserDAO;
import com.example.crudoperations.dto.UserDTO;
import com.example.crudoperations.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        User savedUser = userDAO.saveUser(user);
        return new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.findAllUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userDAO.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userDAO.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        User updatedUser = userDAO.saveUser(existingUser);
        return new UserDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail());
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUserById(id);
    }
}

