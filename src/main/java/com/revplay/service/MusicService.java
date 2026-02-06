package com.revplay.service;

import com.revplay.dao.SongDAO;
import com.revplay.model.Song;

import java.util.List;

public class MusicService {

    private final SongDAO songDAO = new SongDAO();
    private final ListeningHistoryService historyService =
            new ListeningHistoryService();

    // =========================
    // BROWSE SONGS
    // =========================
    public void browseSongs() {
        List<Song> songs = songDAO.getAllSongs();

        if (songs.isEmpty()) {
            System.out.println("❌ No songs available");
            return;
        }

        songs.forEach(System.out::println);
    }

    // =========================
    // SEARCH SONGS
    // =========================
    public void searchSongs(String keyword) {
        List<Song> songs = songDAO.searchSongs(keyword);

        if (songs.isEmpty()) {
            System.out.println("❌ No results found");
            return;
        }

        songs.forEach(System.out::println);
    }

    // =========================
    // PLAY SONG
    // =========================
    public void playSong(int songId, String userEmail) {

        boolean played = songDAO.playSong(songId);

        if (played) {
            System.out.println("▶ Playing song...");
            historyService.saveHistory(userEmail, songId);
        } else {
            System.out.println("❌ Song not found");
        }
    }

    // =========================
    // ARTIST UPLOAD SONG
    // =========================
    public void uploadSong(
            String title,
            String artistName,
            String genre,
            int duration,
            String artistEmail) {

        boolean success = songDAO.uploadSong(
                title, artistName, genre, duration, artistEmail
        );

        if (success) {
            System.out.println("✅ Song uploaded successfully");
        } else {
            System.out.println("❌ Song upload failed");
        }
    }

    // =========================
    // VIEW ARTIST SONG STATS
    // =========================
    public void viewMySongStats(String artistEmail) {

        List<Song> songs = songDAO.getSongStatsByArtist(artistEmail);

        if (songs.isEmpty()) {
            System.out.println("⚠ No songs uploaded yet");
            return;
        }

        System.out.println("\n📊 My Song Statistics 📊");
        for (Song song : songs) {
            System.out.println(
                    "ID: " + song.getSongId() +
                            " | Title: " + song.getTitle() +
                            " | Plays: " + song.getPlayCount()
            );
        }
    }

}
