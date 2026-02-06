package com.revplay.util;

import com.revplay.config.DBConfig;
import com.revplay.exception.RevPlayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static final Logger logger =
            LogManager.getLogger(DBUtil.class);

    public static Connection getConnection() {

        try {
            logger.info("Attempting database connection");

            Connection connection = DriverManager.getConnection(
                    DBConfig.URL,
                    DBConfig.USERNAME,
                    DBConfig.PASSWORD
            );

            logger.info("Database connection established successfully");
            return connection;

        } catch (Exception e) {
            logger.error("Database connection failed", e);
            throw new RevPlayException("Unable to connect to database", e);
        }
    }
}
