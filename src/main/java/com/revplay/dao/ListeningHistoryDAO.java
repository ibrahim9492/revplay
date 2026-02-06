package com.revplay.dao;

import com.revplay.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListeningHistoryDAO {

    private static final Logger logger =
            LogManager.getLogger(ListeningHistoryDAO.class);

    // ===============================
    // SAVE LISTENING HISTORY
    // ===============================
    public void saveHistory(String userEmail, int songId) {

        String sql =
                "INSERT INTO listening_history (user_email, song_id, played_at) " +
                        "VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ps.setInt(2, songId);
            ps.executeUpdate();

            logger.info("Listening history saved for user {} and song {}", userEmail, songId);

        } catch (Exception e) {
            logger.error("Error saving listening history", e);
        }
    }

    // ===============================
    // GET RECENTLY PLAYED SONGS
    // ===============================
    public void getRecentSongs(String userEmail) {

        String sql =
                "SELECT s.song_id, s.title, s.artist " +
                        "FROM listening_history lh " +
                        "JOIN songs s ON lh.song_id = s.song_id " +
                        "WHERE lh.user_email = ? " +
                        "ORDER BY lh.played_at DESC " +
                        "FETCH FIRST 5 ROWS ONLY";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n🎧 Recently Played Songs:");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getInt("song_id") + ". " +
                                rs.getString("title") + " | " +
                                rs.getString("artist")
                );
            }

            if (!found) {
                System.out.println("⚠ No recently played songs");
            }

        } catch (Exception e) {
            logger.error("Error fetching recent songs", e);
        }
    }

    // ===============================
    // GET FULL LISTENING HISTORY
    // ===============================
    public void getListeningHistory(String userEmail) {

        String sql =
                "SELECT s.song_id, s.title, s.artist, lh.played_at " +
                        "FROM listening_history lh " +
                        "JOIN songs s ON lh.song_id = s.song_id " +
                        "WHERE lh.user_email = ? " +
                        "ORDER BY lh.played_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n📜 Listening History:");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getInt("song_id") + ". " +
                                rs.getString("title") + " | " +
                                rs.getString("artist") +
                                " | Played at: " + rs.getTimestamp("played_at")
                );
            }

            if (!found) {
                System.out.println("⚠ No listening history found");
            }

        } catch (Exception e) {
            logger.error("Error fetching listening history", e);
        }
    }
}
