package com.revplay.service;

import com.revplay.dao.SongDAO;
import com.revplay.model.Song;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MusicService {

    private static final Logger logger =
            LogManager.getLogger(MusicService.class);

    private final SongDAO songDAO = new SongDAO();
    private final ListeningHistoryService historyService =
            new ListeningHistoryService();

    // =========================
    // BROWSE SONGS
    // =========================
    public void browseSongs() {
        List<Song> songs = songDAO.getAllSongs();
        songs.forEach(System.out::println);
    }

    // =========================
    // SEARCH SONGS
    // =========================
    public void searchSongs(String keyword) {
        List<Song> songs = songDAO.searchSongs(keyword);
        songs.forEach(System.out::println);
    }

    // =========================
    // PLAY SONG (FIXED)
    // =========================
    public void playSong(int songId, String userEmail) {

        logger.info("Playing song {} for user {}", songId, userEmail);

        if (!songDAO.songExists(songId)) {
            System.out.println("❌ Song not found");
            return;
        }

        boolean played = songDAO.playSong(songId);

        if (played) {
            System.out.println("▶️ Playing song...");
            historyService.saveHistory(userEmail, songId);
        } else {
            System.out.println("❌ Unable to play song");
        }
    }
}
