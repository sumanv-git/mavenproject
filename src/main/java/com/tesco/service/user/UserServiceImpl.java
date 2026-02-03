package com.tesco.service.user;

import com.tesco.dto.UserDto;
import com.tesco.entity.User;
import com.tesco.repositories.jpa.UserJpaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;

    public UserServiceImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = toEntity(userDto);
        User saved = userJpaRepository.save(user);
        return toDto(saved);
    }

    @Override
    public UserDto getUser(String id) {
        User user =
                userJpaRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userJpaRepository.findAll().stream().map(UserServiceImpl::toDto).toList();
    }

    @Override
    public UserDto getUserByName(String username) {
        User user =
                userJpaRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new IllegalArgumentException("User not found: " + username));
        return toDto(user);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        User existing =
                userJpaRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        if (userDto.getUsername() != null) {
            existing.setUsername(userDto.getUsername());
        }

        User saved = userJpaRepository.save(existing);
        return toDto(saved);
    }

    @Override
    public void deleteUser(String id) {
        if (!userJpaRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        userJpaRepository.deleteById(id);
    }

    private static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }

    private static User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        return user;
    }
}
