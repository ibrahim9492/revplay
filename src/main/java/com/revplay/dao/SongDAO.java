package com.revplay.dao;

import com.revplay.model.Song;
import com.revplay.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

    // =========================
    // GET ALL SONGS
    // =========================
    public List<Song> getAllSongs() {

        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs ORDER BY song_id";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                songs.add(mapSong(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return songs;
    }

    // =========================
    // SEARCH SONGS
    // =========================
    public List<Song> searchSongs(String keyword) {

        List<Song> songs = new ArrayList<>();

        String sql =
                "SELECT * FROM songs " +
                        "WHERE LOWER(title) LIKE ? " +
                        "OR LOWER(artist) LIKE ? " +
                        "OR LOWER(genre) LIKE ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String key = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                songs.add(mapSong(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return songs;
    }

    // =========================
    // PLAY SONG (INCREMENT COUNT)
    // =========================
    public boolean playSong(int songId) {

        String sql =
                "UPDATE songs SET play_count = play_count + 1 WHERE song_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, songId);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0; // ✅ SUCCESS

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // GET RECENTLY PLAYED SONGS
    // =========================
    public List<Song> getRecentlyPlayed(String userEmail) {

        List<Song> songs = new ArrayList<>();

        String sql =
                "SELECT s.* FROM songs s " +
                        "JOIN listening_history lh ON s.song_id = lh.song_id " +
                        "WHERE lh.user_email = ? " +
                        "ORDER BY lh.played_at DESC " +
                        "FETCH FIRST 5 ROWS ONLY";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                songs.add(mapSong(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return songs;
    }

    // =========================
    // MAP RESULTSET → SONG
    // =========================
    private Song mapSong(ResultSet rs) throws SQLException {
        return new Song(
                rs.getInt("song_id"),
                rs.getString("title"),
                rs.getString("artist"),
                rs.getString("genre"),
                rs.getInt("duration"),
                rs.getInt("play_count")
        );
    }
}
