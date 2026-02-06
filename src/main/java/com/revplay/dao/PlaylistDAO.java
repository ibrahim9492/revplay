package com.revplay.dao;

import com.revplay.model.Playlist;
import com.revplay.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private static final Logger logger =
            LogManager.getLogger(PlaylistDAO.class);

    // ===============================
    // CREATE PLAYLIST
    // ===============================
    public void createPlaylist(String name,
                               String description,
                               String privacy,
                               String userEmail) {

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
            logger.info("Playlist created for user {}", userEmail);

        } catch (Exception e) {
            logger.error("Error creating playlist", e);
        }
    }

    // ===============================
    // VIEW USER PLAYLISTS
    // ===============================
    public List<Playlist> getUserPlaylists(String userEmail) {

        List<Playlist> playlists = new ArrayList<>();

        String sql = """
            SELECT playlist_id, name, description, privacy, user_email
            FROM playlists
            WHERE user_email = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                playlists.add(new Playlist(
                        rs.getInt("playlist_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("privacy"),
                        rs.getString("user_email")
                ));
            }

        } catch (Exception e) {
            logger.error("Error fetching user playlists", e);
        }

        return playlists;
    }

    // ===============================
    // VIEW PUBLIC PLAYLISTS (STEP 2)
    // ===============================
    public List<Playlist> getPublicPlaylists(String currentUserEmail) {

        List<Playlist> playlists = new ArrayList<>();

        String sql = """
            SELECT playlist_id, name, description, privacy, user_email
            FROM playlists
            WHERE privacy = 'PUBLIC'
              AND user_email <> ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, currentUserEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                playlists.add(new Playlist(
                        rs.getInt("playlist_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("privacy"),
                        rs.getString("user_email")
                ));
            }

        } catch (Exception e) {
            logger.error("Error fetching public playlists", e);
        }

        return playlists;
    }

    // ===============================
    // ADD SONG TO PLAYLIST
    // ===============================
    public void addSongToPlaylist(int playlistId, int songId) {

        String sql = """
            INSERT INTO playlist_songs (playlist_id, song_id)
            VALUES (?, ?)
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);
            ps.executeUpdate();

            logger.info("Song {} added to playlist {}", songId, playlistId);

        } catch (Exception e) {
            logger.error("Error adding song to playlist", e);
        }
    }

    // ===============================
    // REMOVE SONG FROM PLAYLIST
    // ===============================
    public void removeSongFromPlaylist(int playlistId, int songId) {

        String sql = """
            DELETE FROM playlist_songs
            WHERE playlist_id = ? AND song_id = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);
            ps.executeUpdate();

            logger.info("Song {} removed from playlist {}", songId, playlistId);

        } catch (Exception e) {
            logger.error("Error removing song from playlist", e);
        }
    }

    // ===============================
    // UPDATE PLAYLIST
    // ===============================
    public void updatePlaylist(int playlistId,
                               String name,
                               String description,
                               String privacy) {

        String sql = """
            UPDATE playlists
            SET name = ?, description = ?, privacy = ?
            WHERE playlist_id = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, privacy);
            ps.setInt(4, playlistId);

            ps.executeUpdate();
            logger.info("Playlist {} updated", playlistId);

        } catch (Exception e) {
            logger.error("Error updating playlist", e);
        }
    }

    // ===============================
    // DELETE PLAYLIST
    // ===============================
    public void deletePlaylist(int playlistId) {

        String sql = "DELETE FROM playlists WHERE playlist_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.executeUpdate();

            logger.info("Playlist {} deleted", playlistId);

        } catch (Exception e) {
            logger.error("Error deleting playlist", e);
        }
    }
}
