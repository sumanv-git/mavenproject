package com.tesco.service;

import com.tesco.model.User;
import com.tesco.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private IdGenerator idGenerator;

    public UserServiceImpl(UserRepository userRepository, IdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public User addUser(User user) {

        if(Objects.nonNull(user.getId())){
            throw new RuntimeException("User id should not be null for new user");
        }
        user.setId(idGenerator.generateId());
        return userRepository.save(user);
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    public void updateUser(String id, User updatedUser) {
        userRepository.updateUser(id, updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteUser(id);
    }
}
