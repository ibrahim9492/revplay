package com.revplay;

import com.revplay.model.User;
import com.revplay.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserServiceTest {

    private final UserService userService = new UserService();

    // Utility method to generate unique email every time
    private String uniqueEmail() {
        return "user_" + UUID.randomUUID() + "@gmail.com";
    }

    @Test
    void testRegisterUser() {

        String email = uniqueEmail();
        String password = "test123";

        boolean result = userService.register(email, password, "USER");

        Assertions.assertTrue(
                result,
                "User should be registered successfully"
        );
    }

    @Test
    void testLoginValidUser() {

        // ARRANGE
        String email = uniqueEmail();
        String password = "test123";

        userService.register(email, password, "USER");

        // ACT
        User user = userService.login(email, password);

        // ASSERT
        Assertions.assertNotNull(
                user,
                "User should login successfully"
        );

        Assertions.assertEquals(email, user.getEmail());
    }

    @Test
    void testLoginInvalidUser() {

        User user = userService.login(
                "wronguser@gmail.com",
                "wrongpass"
        );

        Assertions.assertNull(
                user,
                "Invalid user should not be able to login"
        );
    }
}
