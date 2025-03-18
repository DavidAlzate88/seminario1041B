package co.edu.uniajc.controller;

import co.edu.uniajc.model.User;
import co.edu.uniajc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

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
        when(userService.save(testUser)).thenReturn(testUser);

        ResponseEntity<User> response = userController.save(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
        verify(userService, times(1)).save(testUser);
    }

    @Test
    void save_badRequest() {
        when(userService.save(testUser)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<User> response = userController.save(testUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).save(testUser);
    }

    @Test
    void getUserByEmail_success() {
        when(userService.findByEmail("test@example.com")).thenReturn(testUser);

        ResponseEntity<User> response = userController.getUserByEmail("test@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
        verify(userService, times(1)).findByEmail("test@example.com");
    }

    @Test
    void getUserByEmail_notFound() {
        when(userService.findByEmail("nonexistent@example.com")).thenReturn(null);

        ResponseEntity<User> response = userController.getUserByEmail("nonexistent@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).findByEmail("nonexistent@example.com");
    }
}