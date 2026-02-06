package com.revplay.dao;

import com.revplay.model.Song;
import com.revplay.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    private static final Logger logger =
            LogManager.getLogger(FavoriteDAO.class);

    // ===============================
    // ADD SONG TO FAVORITES
    // ===============================
    public void addToFavorites(String userEmail, int songId) {

        String sql = """
            INSERT INTO favorites (user_email, song_id)
            SELECT ?, ?
            FROM dual
            WHERE NOT EXISTS (
                SELECT 1 FROM favorites
                WHERE user_email = ? AND song_id = ?
            )
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ps.setInt(2, songId);
            ps.setString(3, userEmail);
            ps.setInt(4, songId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                logger.info("Song {} added to favorites for {}", songId, userEmail);
            } else {
                logger.warn("Duplicate favorite prevented for song {}", songId);
            }

        } catch (Exception e) {
            logger.error("Error adding to favorites", e);
        }
    }

    // ===============================
    // VIEW FAVORITE SONGS
    // ===============================
    public List<Song> getFavoriteSongs(String userEmail) {

        List<Song> songs = new ArrayList<>();

        String sql = """
            SELECT s.song_id, s.title, s.artist, s.genre, s.duration, s.play_count
            FROM songs s
            JOIN favorites f ON s.song_id = f.song_id
            WHERE f.user_email = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                songs.add(new Song(
                        rs.getInt("song_id"),
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getString("genre"),
                        rs.getInt("duration"),
                        rs.getInt("play_count")
                ));
            }

        } catch (Exception e) {
            logger.error("Error fetching favorite songs", e);
        }

        return songs;
    }
}
