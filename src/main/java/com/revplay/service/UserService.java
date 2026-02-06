package com.revplay.service;

import com.revplay.dao.UserDAO;
import com.revplay.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private static final Logger logger =
            LogManager.getLogger(UserService.class);

    private final UserDAO userDAO = new UserDAO();

    // REGISTER
    public boolean register(String email, String password, String role) {

        logger.info("User registration attempt for email: {}", email);

        boolean result = userDAO.register(email, password, role);

        if (result) {
            logger.info("User registered successfully: {}", email);
        } else {
            logger.warn("User registration failed: {}", email);
        }

        return result;
    }

    // LOGIN
    public User login(String email, String password) {

        logger.info("Login attempt for email: {}", email);

        User user = userDAO.login(email, password);

        if (user != null) {
            logger.info("Login successful for email: {}", email);
        } else {
            logger.warn("Login failed for email: {}", email);
        }

        return user;
    }
}
