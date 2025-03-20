package com.tesco.service;

import com.tesco.model.User;
import com.tesco.repositories.InMemoryUserRepositry;
import com.tesco.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

 UserService userService;

 UserRepository userRepository;

 IdGenerator idGenerator;

 private static final UUID USER_ID = UUID.randomUUID();

 private static final User USER = new User("john", "1234567890", "john@example.com");

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
       idGenerator = mock(IdGenerator.class);
        userService = new UserServiceImpl(userRepository,idGenerator);
    }

    @Test
    void shouldSaveUserSuccessfully() {
        // Arrange
        User user = new User();
        String generatedId = "some-id";

        when(idGenerator.generateId()).thenReturn(generatedId);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.addUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);  // Ensure save() is called exactly once
        verify(idGenerator, times(1)).generateId();     // Ensure ID is generated once
    }
    @Test
    void shouldFindUserByIdSuccessfully() {}

    @Test
    void ShouldSaveUser(){
        //given
        when(idGenerator.generateId()).thenReturn(String.valueOf(USER_ID));
        when(userRepository.save(any(User.class))).thenReturn(USER);
        //when
          var savedUser = userService.addUser(USER);
        //then
        assertThat(savedUser).isSameAs(USER);
    }

    @Test
    void shouldFindUserByNameSuccessfully() {
        //given
        when(userRepository.findUserByName(USER.getUsername())).thenReturn(USER);

        //when
        var foundUser = userService.findUserByName(USER.getUsername());

        //then
        assertThat(foundUser).isSameAs(USER);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        //given
        doNothing().when(userRepository).deleteUser(anyString());

        //when
        userService.deleteUser(USER_ID.toString());

        //then
        verify(userRepository, times(1)).deleteUser(USER_ID.toString());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        //given
        User updatedUser = new User("john_updated", "1234567890", "john_updated@example.com");
        doNothing().when(userRepository).updateUser(anyString(), any(User.class));

        //when
        userService.updateUser(USER_ID.toString(), updatedUser);

        //then
        verify(userRepository, times(1)).updateUser(USER_ID.toString(), updatedUser);
    }

    @Test
    void shouldUpdateUserWithAssert(){
        // Given
        String userId = "12345";  // Mocked user ID
        when(idGenerator.generateId()).thenReturn(userId); // Corrected order

        User existingUser = new User("john", "1234567890", "john@example.com");
        existingUser.setId(userId);
        userRepository.save(existingUser);

        User updatedUser = new User("john_updated", "0987654321", "john_updated@example.com");
        updatedUser.setId(userId);


        // When
        userService.updateUser(userId, updatedUser);

        // Then
        //verify(userRepository, times(1)).updateUser(eq(userId), any(User.class));  // Ensures updateUser() was called
        User retrievedUser = userService.getUser(userId);

        // Assertions
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getUsername()).isEqualTo("john_updated");
        assertThat(retrievedUser.getMobileNumber()).isEqualTo("0987654321");
        assertThat(retrievedUser.getEmail()).isEqualTo("john_updated@example.com");
    }

    @Test
    void shouldGetAllUsersSuccessfully() {
        //given
        User user1 = new User("john1", "1234567890", "john1@example.com");
        User user2 = new User("john2", "0987654321", "john2@example.com");
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.getAllUsers()).thenReturn(users);

        //when
        var allUsers = userService.getAllUsers();

        //then
        assertThat(allUsers).containsExactlyInAnyOrder(user1, user2);
    }
}