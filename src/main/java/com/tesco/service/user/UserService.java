package com.tesco.service.user;

import com.tesco.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUser(String id);

    List<UserDto> getAllUsers();

    UserDto getUserByName(String username);

    UserDto updateUser(String id, UserDto userDto);

    void deleteUser(String id);
}
