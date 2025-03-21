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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private final String testEmail = "test@example.com";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("Test User")
                .email(testEmail)
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
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByEmail(testEmail);

        assertEquals(testUser, foundUser);
        verify(userRepository, times(1)).findByEmail(testEmail);
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
}