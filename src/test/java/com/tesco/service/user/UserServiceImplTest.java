package com.tesco.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tesco.dto.UserDto;
import com.tesco.entity.User;
import com.tesco.repositories.jpa.UserJpaRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {

    private UserService userService;

    private UserJpaRepository userJpaRepository;

    @BeforeEach
    void setUp() {
        userJpaRepository = mock(UserJpaRepository.class);
        userService = new UserServiceImpl(userJpaRepository);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        UserDto request = new UserDto(null, "john");
        User savedEntity = new User("john", "1234567890", "john@example.com");
        savedEntity.setId("some-id");

        when(userJpaRepository.save(any(User.class))).thenReturn(savedEntity);

        // Act
        UserDto created = userService.createUser(request);

        // Assert
        assertThat(created.getId()).isEqualTo("some-id");
        assertThat(created.getUsername()).isEqualTo("john");
        verify(userJpaRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        // Arrange
        String id = "some-id";
        User user = new User("john", "1234567890", "john@example.com");
        user.setId(id);

        when(userJpaRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        UserDto found = userService.getUser(id);

        // Assert
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getUsername()).isEqualTo("john");
        verify(userJpaRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowWhenUserNotFoundById() {
        // Arrange
        String id = "missing";
        when(userJpaRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> userService.getUser(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");
        verify(userJpaRepository, times(1)).findById(id);
    }

    @Test
    void shouldFindUserByNameSuccessfully() {
        // Arrange
        User user = new User("john", "1234567890", "john@example.com");
        user.setId("some-id");
        when(userJpaRepository.findByUsername("john")).thenReturn(Optional.of(user));

        // Act
        UserDto found = userService.getUserByName("john");

        // Assert
        assertThat(found.getUsername()).isEqualTo("john");
        assertThat(found.getId()).isEqualTo("some-id");
        verify(userJpaRepository, times(1)).findByUsername("john");
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        // Arrange
        String id = "some-id";
        when(userJpaRepository.existsById(id)).thenReturn(true);

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userJpaRepository, times(1)).existsById(id);
        verify(userJpaRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeletingMissingUser() {
        // Arrange
        String id = "missing";
        when(userJpaRepository.existsById(id)).thenReturn(false);

        // Act + Assert
        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");

        verify(userJpaRepository, times(1)).existsById(id);
        verify(userJpaRepository, never()).deleteById(anyString());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        String id = "some-id";
        User existing = new User("john", "1234567890", "john@example.com");
        existing.setId(id);

        when(userJpaRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userJpaRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserDto update = new UserDto(null, "john_updated");

        // Act
        UserDto updated = userService.updateUser(id, update);

        // Assert
        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getUsername()).isEqualTo("john_updated");

        verify(userJpaRepository, times(1)).findById(id);
        verify(userJpaRepository, times(1)).save(existing);
    }

    @Test
    void shouldGetAllUsersSuccessfully() {
        // Arrange
        User user1 = new User("john1", "1234567890", "john1@example.com");
        user1.setId("1");
        User user2 = new User("john2", "0987654321", "john2@example.com");
        user2.setId("2");

        when(userJpaRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<UserDto> allUsers = userService.getAllUsers();

        // Assert
        assertThat(allUsers).hasSize(2);
        assertThat(allUsers.stream().map(UserDto::getId).toList())
                .containsExactlyInAnyOrder("1", "2");
        assertThat(allUsers.stream().map(UserDto::getUsername).toList())
                .containsExactlyInAnyOrder("john1", "john2");

        verify(userJpaRepository, times(1)).findAll();
    }
}
