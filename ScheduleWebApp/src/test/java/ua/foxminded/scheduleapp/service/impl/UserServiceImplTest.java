package ua.foxminded.scheduleapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.foxminded.scheduleapp.model.User;
import ua.foxminded.scheduleapp.repository.UserRepository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
    }

    @Test
    void updateUser_withNewPassword_shouldUpdatePassword() {
        String newPassword = "newPassword";
        user.setPassword(newPassword);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        userService.updateUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_withNullPassword_shouldRetainOldPassword() {
        user.setPassword(null);
        User existingUser = new User();
        existingUser.setPassword("existingPassword");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(existingUser));

        userService.updateUser(user);

        assertEquals("existingPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_withEmptyPassword_shouldRetainOldPassword() {
        user.setPassword("");
        User existingUser = new User();
        existingUser.setPassword("existingPassword");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(existingUser));

        userService.updateUser(user);

        assertEquals("existingPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}

