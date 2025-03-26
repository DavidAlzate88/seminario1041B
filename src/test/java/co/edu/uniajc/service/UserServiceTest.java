package co.edu.uniajc.service;

import co.edu.uniajc.exception.UserException;
import co.edu.uniajc.model.User;
import co.edu.uniajc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_NAME = "Test User";
    private static final String TEST_ROLE_NAME = "Client";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .build();
    }

    @Test
    void saveSuccess() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User savedUser = userService.save(testUser);

        assertEquals(testUser, savedUser);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void saveEmailAlreadyInUse() {
        when(userRepository.save(testUser)).thenThrow(new DataIntegrityViolationException("Email already in use"));

        assertThrows(UserException.class, () -> userService.save(testUser));
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void findByEmailSuccess() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByEmail(TEST_EMAIL);

        assertEquals(testUser, foundUser);
        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    }

    @Test
    void findByEmailNotFound() {
        String testNonExistentEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(testNonExistentEmail)).thenReturn(Optional.empty());

        User foundUser = userService.findByEmail(testNonExistentEmail);

        assertNull(foundUser);
        verify(userRepository, times(1)).findByEmail(testNonExistentEmail);
    }

    @Test
    void findByEmailException() {
        String errorEmail = "error@example.com";
        when(userRepository.findByEmail(errorEmail)).thenThrow(new RuntimeException("Database error"));

        assertThrows(UserException.class, () -> userService.findByEmail(errorEmail));
        verify(userRepository, times(1)).findByEmail(errorEmail);
    }

    @Test
    void findByNameSuccess() {
        when(userRepository.findByName(TEST_NAME)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByName(TEST_NAME);

        assertEquals(testUser, foundUser);
        verify(userRepository, times(1)).findByName(TEST_NAME);
    }

    @Test
    void findByNameNotFound() {
        String testNonExistentName = "Nonexistent User";
        when(userRepository.findByName(testNonExistentName)).thenReturn(Optional.empty());

        User foundUser = userService.findByName(testNonExistentName);

        assertNull(foundUser);
        verify(userRepository, times(1)).findByName(testNonExistentName);
    }

    @Test
    void findByNameException() {
        String errorName = "Error User";
        when(userRepository.findByName(errorName)).thenThrow(new RuntimeException("Database error"));

        assertThrows(UserException.class, () -> userService.findByName(errorName));
        verify(userRepository, times(1)).findByName(errorName);
    }

    @Test
    void findUsersByRoleNameSuccess() {
        List<User> users = new ArrayList<>();
        users.add(testUser);
        when(userRepository.findUsersByRoleName(TEST_ROLE_NAME)).thenReturn(users);

        List<User> foundUsers = userService.findUsersByRoleName(TEST_ROLE_NAME);

        assertEquals(users, foundUsers);
        verify(userRepository, times(1)).findUsersByRoleName(TEST_ROLE_NAME);
    }

    @Test
    void findUsersByRoleNameException() {
        when(userRepository.findUsersByRoleName(TEST_ROLE_NAME)).thenThrow(new RuntimeException("Database error"));

        assertThrows(UserException.class, () -> userService.findUsersByRoleName(TEST_ROLE_NAME));
        verify(userRepository, times(1)).findUsersByRoleName(TEST_ROLE_NAME);
    }
}