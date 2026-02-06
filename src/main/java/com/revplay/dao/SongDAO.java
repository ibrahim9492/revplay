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
                "SELECT * FROM songs WHERE LOWER(title) LIKE ? " +
                        "OR LOWER(artist) LIKE ? OR LOWER(genre) LIKE ?";

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
    // PLAY SONG
    // =========================
    public boolean playSong(int songId) {

        String sql =
                "UPDATE songs SET play_count = play_count + 1 WHERE song_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, songId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // ARTIST UPLOAD SONG
    // =========================
    public boolean uploadSong(
            String title,
            String artistName,
            String genre,
            int duration,
            String artistEmail) {

        String sql =
                "INSERT INTO songs (title, artist, genre, duration, play_count, artist_email) " +
                        "VALUES (?, ?, ?, ?, 0, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, artistName);
            ps.setString(3, genre);
            ps.setInt(4, duration);
            ps.setString(5, artistEmail);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // VIEW ARTIST SONGS
    // =========================
    public List<Song> getSongsByArtist(String artistEmail) {

        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs WHERE artist_email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, artistEmail);
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
    // ARTIST SONG STATISTICS
    // =========================
    public List<Song> getSongStatsByArtist(String artistEmail) {

        List<Song> songs = new ArrayList<>();

        String sql =
                "SELECT * FROM songs WHERE artist_email = ? ORDER BY play_count DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, artistEmail);
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
    // MAP SONG
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
