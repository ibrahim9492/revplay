package com.revplay.service;

import com.revplay.dao.SongDAO;
import com.revplay.dao.UserDAO;
import com.revplay.model.Song;
import com.revplay.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ArtistService {

    private static final Logger logger =
            LogManager.getLogger(ArtistService.class);

    private final UserDAO userDAO = new UserDAO();
    private final SongDAO songDAO = new SongDAO();

    // =========================
    // REGISTER ARTIST
    // =========================
    public boolean registerArtist(String email, String password) {

        boolean success = userDAO.register(email, password, "ARTIST");

        if (success) {
            System.out.println("✅ Artist registered successfully");
            logger.info("Artist registered: {}", email);
        } else {
            System.out.println("❌ Artist registration failed");
        }

        return success;
    }

    // =========================
    // LOGIN ARTIST
    // =========================
    public User loginArtist(String email, String password) {

        User user = userDAO.login(email, password);

        if (user == null || !"ARTIST".equalsIgnoreCase(user.getRole())) {
            System.out.println("❌ Invalid artist credentials");
            return null;
        }

        System.out.println("🎉 Artist login successful");
        logger.info("Artist logged in: {}", email);
        return user;
    }

    // =========================
    // UPLOAD SONG
    // =========================
    public void uploadSong(String title,
                           String artistEmail,
                           String genre,
                           int duration) {

        songDAO.uploadSong(title, artistEmail, genre, duration);
        System.out.println("✅ Song uploaded successfully");
    }

    // =========================
    // VIEW SONG STATISTICS
    // =========================
    public void viewSongStats(String artistEmail) {

        List<Song> songs = songDAO.getArtistSongs(artistEmail);

        if (songs.isEmpty()) {
            System.out.println("ℹ️ No songs uploaded yet");
            return;
        }

        System.out.println("\n🎵 My Song Statistics");
        for (Song song : songs) {
            System.out.println(
                    song.getSongId() + " | " +
                            song.getTitle() + " | Plays: " +
                            song.getPlayCount()
            );
        }
    }
}
