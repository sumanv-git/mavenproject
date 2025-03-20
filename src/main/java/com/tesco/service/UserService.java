package com.tesco.service;

import com.tesco.model.User;

import java.util.List;

public interface UserService {
    User  addUser(User user);

    User getUser(String id);

    List<User> getAllUsers();
    User findUserByName(String name);

    void updateUser(String id, User updatedUser);

    void deleteUser(String id);
}
