package com.revplay.dao;

import com.revplay.model.Playlist;
import com.revplay.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private static final Logger logger =
            LogManager.getLogger(PlaylistDAO.class);

    // =========================
    // CREATE PLAYLIST
    // =========================
    public void createPlaylist(
            String name,
            String description,
            String privacy,
            String userEmail
    ) {
        String sql = """
            INSERT INTO playlists (name, description, privacy, user_email)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, privacy);
            ps.setString(4, userEmail);
            ps.executeUpdate();

        } catch (Exception e) {
            logger.error("Error creating playlist", e);
        }
    }

    // =========================
    // GET USER PLAYLISTS
    // =========================
    public List<Playlist> getUserPlaylists(String userEmail) {

        List<Playlist> playlists = new ArrayList<>();

        String sql = """
            SELECT playlist_id, name, privacy
            FROM playlists
            WHERE user_email = ?
            ORDER BY playlist_id
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                playlists.add(
                        new Playlist(
                                rs.getInt("playlist_id"),
                                rs.getString("name"),
                                rs.getString("privacy")
                        )
                );
            }

        } catch (Exception e) {
            logger.error("Error fetching playlists", e);
        }

        return playlists;
    }

    // =========================
    // VALIDATIONS
    // =========================
    public boolean playlistExists(int playlistId) {
        String sql = "SELECT 1 FROM playlists WHERE playlist_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            return ps.executeQuery().next();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOwnedByUser(int playlistId, String userEmail) {
        String sql =
                "SELECT 1 FROM playlists WHERE playlist_id = ? AND user_email = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setString(2, userEmail);
            return ps.executeQuery().next();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean songAlreadyInPlaylist(int playlistId, int songId) {
        String sql =
                "SELECT 1 FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);
            return ps.executeQuery().next();

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // ADD / REMOVE / DELETE
    // =========================
    public void addSongToPlaylist(int playlistId, int songId) {
        String sql =
                "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);
            ps.executeUpdate();

        } catch (Exception e) {
            logger.error("Error adding song to playlist", e);
        }
    }

    public void removeSongFromPlaylist(int playlistId, int songId) {
        String sql =
                "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);
            ps.executeUpdate();

        } catch (Exception e) {
            logger.error("Error removing song from playlist", e);
        }
    }

    public void deletePlaylist(int playlistId) {
        String sql = "DELETE FROM playlists WHERE playlist_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.executeUpdate();

        } catch (Exception e) {
            logger.error("Error deleting playlist", e);
        }
    }
}
