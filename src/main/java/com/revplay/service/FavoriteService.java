package com.revplay.service;

import com.revplay.dao.FavoriteDAO;
import com.revplay.model.Song;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FavoriteService {

    private static final Logger logger =
            LogManager.getLogger(FavoriteService.class);

    private final FavoriteDAO favoriteDAO = new FavoriteDAO();

    // ===============================
    // ADD SONG TO FAVORITES
    // ===============================
    public void addToFavorites(String userEmail, int songId) {

        logger.info("Adding song {} to favorites for {}", songId, userEmail);

        favoriteDAO.addToFavorites(userEmail, songId);

        System.out.println("⭐ Song added to favorites (if not already)");
    }

    // ===============================
    // VIEW FAVORITE SONGS
    // ===============================
    public void viewFavoriteSongs(String userEmail) {

        logger.info("Fetching favorite songs for {}", userEmail);

        List<Song> songs = favoriteDAO.getFavoriteSongs(userEmail);

        if (songs.isEmpty()) {
            System.out.println("⚠️ No favorite songs found");
            return;
        }

        System.out.println("\n⭐ Your Favorite Songs ⭐");
        for (Song s : songs) {
            System.out.println(
                    s.getSongId() + ". " +
                            s.getTitle() + " - " +
                            s.getArtist() +
                            " (" + s.getGenre() + ")"
            );
        }
    }
}
