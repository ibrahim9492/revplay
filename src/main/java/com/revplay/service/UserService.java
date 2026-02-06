package com.revplay.service;

import com.revplay.dao.UserDAO;
import com.revplay.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private static final Logger logger =
            LogManager.getLogger(UserService.class);

    private final UserDAO userDAO = new UserDAO();

    // =========================
    // CORE REGISTER (USED BY TESTS)
    // =========================
    public boolean register(String email, String password, String role) {
        logger.info("Registering {} with role {}", email, role);
        return userDAO.register(email, password, role);
    }

    // =========================
    // USER REGISTER (APP MENU)
    // =========================
    public boolean registerUser(String email, String password) {
        return register(email, password, "USER");
    }

    // =========================
    // ARTIST REGISTER (APP MENU)
    // =========================
    public boolean registerArtist(String email, String password) {
        return register(email, password, "ARTIST");
    }

    // =========================
    // LOGIN
    // =========================
    public User login(String email, String password) {
        logger.info("Login attempt for {}", email);
        return userDAO.login(email, password);
    }
}
