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
    // BROWSE ALL SONGS
    // =========================
    public void browseSongs() {

        logger.info("Browsing all songs");

        List<Song> songs = songDAO.getAllSongs();

        if (songs.isEmpty()) {
            System.out.println("❌ No songs available");
            return;
        }

        System.out.println("\n🎵 Available Songs 🎵");
        for (Song song : songs) {
            System.out.println(song);
        }
    }

    // =========================
    // SEARCH SONGS
    // =========================
    public void searchSongs(String keyword) {

        logger.info("Searching songs with keyword: {}", keyword);

        List<Song> songs = songDAO.searchSongs(keyword);

        if (songs.isEmpty()) {
            System.out.println("❌ No songs found");
            return;
        }

        System.out.println("\n🔍 Search Results 🔍");
        for (Song song : songs) {
            System.out.println(song);
        }
    }

    // =========================
    // PLAY SONG
    // =========================
    public void playSong(int songId, String userEmail) {

        logger.info("Playing song {} for user {}", songId, userEmail);

        boolean played = songDAO.playSong(songId);

        if (played) {
            System.out.println("▶ Playing song...");
            historyService.saveHistory(userEmail, songId);
        } else {
            System.out.println("❌ Unable to play song");
        }
    }
}
