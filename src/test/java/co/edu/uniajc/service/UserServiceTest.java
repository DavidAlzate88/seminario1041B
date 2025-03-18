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
                .email("test@example.com")
                .build();
    }

    @Test
    void save_success() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User savedUser = userService.save(testUser);

        assertEquals(testUser, savedUser);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void save_emailAlreadyInUse() {
        when(userRepository.save(testUser)).thenThrow(new DataIntegrityViolationException("Email already in use"));

        assertThrows(UserException.class, () -> userService.save(testUser));
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void findByEmail_success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByEmail("test@example.com");

        assertEquals(testUser, foundUser);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void findByEmail_notFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        User foundUser = userService.findByEmail("nonexistent@example.com");

        assertNull(foundUser);
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void findByEmail_exception() {
        when(userRepository.findByEmail("error@example.com")).thenThrow(new RuntimeException("Database error"));

        assertThrows(UserException.class, () -> userService.findByEmail("error@example.com"));
        verify(userRepository, times(1)).findByEmail("error@example.com");
    }
}