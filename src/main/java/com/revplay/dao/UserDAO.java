package com.revplay.dao;

import com.revplay.model.User;
import com.revplay.util.DBUtil;
import com.revplay.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private static final Logger logger =
            LogManager.getLogger(UserDAO.class);

    // =========================
    // REGISTER USER
    // =========================
    public boolean register(String email, String password, String role) {

        String sql =
                "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            // 🔐 HASH PASSWORD BEFORE STORING
            ps.setString(2, PasswordUtil.hashPassword(password));

            ps.setString(3, role);

            int rows = ps.executeUpdate();

            logger.info("Inserted {} user record(s) for email {}", rows, email);

            return rows > 0;

        } catch (Exception e) {
            logger.error("Error registering user {}", email, e);
            return false;
        }
    }

    // =========================
    // LOGIN USER
    // =========================
    public User login(String email, String password) {

        String sql =
                "SELECT email, password, role FROM users WHERE email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String dbPassword = rs.getString("password");

                // 🔐 VERIFY HASHED PASSWORD
                if (PasswordUtil.verifyPassword(password, dbPassword)) {

                    logger.info("User authenticated: {}", email);

                    return new User(
                            rs.getString("email"),
                            rs.getString("role")
                    );
                }
            }

            logger.warn("Login failed for email {}", email);
            return null;

        } catch (Exception e) {
            logger.error("Login error for email {}", email, e);
            return null;
        }
    }
}
