package com.tesco.repositories;

import com.tesco.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String id);

    List<User> getAllUsers();

    User findUserByName(String name);

    void updateUser(String id, User updatedUser);

    void deleteUser(String id);
}
