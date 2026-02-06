package com.revplay.service;

import com.revplay.dao.ListeningHistoryDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListeningHistoryService {

    private static final Logger logger =
            LogManager.getLogger(ListeningHistoryService.class);

    private final ListeningHistoryDAO historyDAO = new ListeningHistoryDAO();

    public void saveHistory(String userEmail, int songId) {
        logger.info("Saving listening history");
        historyDAO.saveHistory(userEmail, songId);
    }

    public void viewRecent(String userEmail) {
        logger.info("Viewing recently played songs");
        historyDAO.getRecentSongs(userEmail);
    }

    public void viewHistory(String userEmail) {
        logger.info("Viewing listening history");
        historyDAO.getListeningHistory(userEmail);
    }
}
