package com.revplay.service;

import com.revplay.dao.PlaylistDAO;
import com.revplay.model.Playlist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PlaylistService {

    private static final Logger logger =
            LogManager.getLogger(PlaylistService.class);

    private final PlaylistDAO playlistDAO = new PlaylistDAO();

    // ===============================
    // CREATE PLAYLIST
    // ===============================
    public void createPlaylist(String name, String description,
                               String privacy, String userEmail) {

        logger.info("Creating playlist '{}' for user {}", name, userEmail);
        playlistDAO.createPlaylist(name, description, privacy, userEmail);
        System.out.println("✅ Playlist created successfully");
    }

    // ===============================
    // VIEW USER PLAYLISTS
    // ===============================
    public void viewUserPlaylists(String userEmail) {
        logger.info("Fetching playlists for user {}", userEmail);

        List<Playlist> playlists = playlistDAO.getUserPlaylists(userEmail);

        if (playlists.isEmpty()) {
            System.out.println("⚠ No playlists found");
            return;
        }

        System.out.println("\n🎶 Your Playlists:");
        for (Playlist p : playlists) {
            System.out.println(
                    p.getPlaylistId() + ". " +
                            p.getName() + " | " +
                            p.getDescription() + " | " +
                            p.getPrivacy()
            );
        }
    }

    // ===============================
    // ADD SONG TO PLAYLIST
    // ===============================
    public void addSong(int playlistId, int songId) {
        logger.info("Adding song {} to playlist {}", songId, playlistId);
        playlistDAO.addSongToPlaylist(playlistId, songId);
        System.out.println("✅ Song added to playlist");
    }

    // ===============================
    // REMOVE SONG FROM PLAYLIST
    // ===============================
    public void removeSong(int playlistId, int songId) {
        logger.info("Removing song {} from playlist {}", songId, playlistId);
        playlistDAO.removeSongFromPlaylist(playlistId, songId);
        System.out.println("✅ Song removed from playlist");
    }

    // ===============================
    // DELETE PLAYLIST
    // ===============================
    public void deletePlaylist(int playlistId) {
        logger.info("Deleting playlist {}", playlistId);
        playlistDAO.deletePlaylist(playlistId);
        System.out.println("🗑 Playlist deleted successfully");
    }
}
