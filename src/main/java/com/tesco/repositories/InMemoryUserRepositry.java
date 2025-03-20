package com.tesco.repositories;

import com.tesco.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepositry implements UserRepository{

    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserRepositry.class);

    private Map<String, User> userMap = new ConcurrentHashMap<>();
    @Override
    public User save(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return  Optional.ofNullable(userMap.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }

    @Override
    public User findUserByName(String name) {
        for (User user : userMap.values()) {
            if (user.getUsername().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void updateUser(String id, User updatedUser) {
        userMap.put(id,updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        userMap.remove(id);
    }
}
