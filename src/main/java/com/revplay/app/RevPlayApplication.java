package com.revplay.app;

import com.revplay.model.User;
import com.revplay.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class RevPlayApplication {

    private static final Logger logger =
            LogManager.getLogger(RevPlayApplication.class);

    public static void main(String[] args) {

        logger.info("RevPlay Application Started");

        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService();
        MusicService musicService = new MusicService();
        PlaylistService playlistService = new PlaylistService();
        FavoriteService favoriteService = new FavoriteService();
        ListeningHistoryService historyService = new ListeningHistoryService();

        User loggedInUser = null;

        while (true) {

            System.out.println("\n🎵 ===== RevPlay Menu ===== 🎵");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Browse Songs");
            System.out.println("4. Search Songs");
            System.out.println("5. Play Song");
            System.out.println("6. View Recently Played");
            System.out.println("7. View Listening History");
            System.out.println("8. Add Song to Favorites");
            System.out.println("9. View Favorite Songs");
            System.out.println("10. Create Playlist");
            System.out.println("11. View My Playlists");
            System.out.println("12. Add Song to Playlist");
            System.out.println("13. Remove Song from Playlist");
            System.out.println("14. Delete Playlist");
            System.out.println("15. Logout");
            System.out.println("16. Exit");

            // 🎤 ARTIST OPTIONS
            System.out.println("17. Register Artist");
            System.out.println("18. Upload Song (Artist)");
            System.out.println("19. View My Song Statistics (Artist)");

            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            logger.info("Menu option selected: {}", choice);

            switch (choice) {

                // ================= USER =================

                case 1 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    boolean success = userService.registerUser(email, password);
                    System.out.println(success
                            ? "✅ User registered successfully"
                            : "❌ Registration failed");
                }

                case 2 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    loggedInUser = userService.login(email, password);

                    if (loggedInUser != null) {
                        System.out.println("🎉 Login successful (" +
                                loggedInUser.getRole() + ")");
                    } else {
                        System.out.println("❌ Invalid credentials");
                    }
                }

                case 3 -> musicService.browseSongs();

                case 4 -> {
                    System.out.print("Enter keyword: ");
                    String keyword = scanner.nextLine();
                    musicService.searchSongs(keyword);
                }

                case 5 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    System.out.print("Enter Song ID: ");
                    int songId = scanner.nextInt();
                    scanner.nextLine();

                    musicService.playSong(songId, loggedInUser.getEmail());
                }

                case 6 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    historyService.viewRecent(loggedInUser.getEmail());
                }

                case 7 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    historyService.viewHistory(loggedInUser.getEmail());
                }

                case 8 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    System.out.print("Enter Song ID: ");
                    int songId = scanner.nextInt();
                    scanner.nextLine();

                    favoriteService.addToFavorites(
                            loggedInUser.getEmail(), songId
                    );
                }

                case 9 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    favoriteService.viewFavoriteSongs(
                            loggedInUser.getEmail()
                    );
                }

                case 10 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    System.out.print("Playlist Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Description: ");
                    String description = scanner.nextLine();

                    System.out.print("Privacy (PUBLIC / PRIVATE): ");
                    String privacy = scanner.nextLine().toUpperCase();

                    playlistService.createPlaylist(
                            name,
                            description,
                            privacy,
                            loggedInUser.getEmail()
                    );
                }

                case 11 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    playlistService.viewUserPlaylists(
                            loggedInUser.getEmail()
                    );
                }

                case 12 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    System.out.print("Playlist ID: ");
                    int playlistId = scanner.nextInt();

                    System.out.print("Song ID: ");
                    int songId = scanner.nextInt();
                    scanner.nextLine();

                    playlistService.addSong(playlistId, songId);
                }

                case 13 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    System.out.print("Playlist ID: ");
                    int playlistId = scanner.nextInt();

                    System.out.print("Song ID: ");
                    int songId = scanner.nextInt();
                    scanner.nextLine();

                    playlistService.removeSong(playlistId, songId);
                }

                case 14 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    System.out.print("Playlist ID: ");
                    int playlistId = scanner.nextInt();
                    scanner.nextLine();

                    playlistService.deletePlaylist(playlistId);
                }

                case 15 -> {
                    loggedInUser = null;
                    System.out.println("👋 Logged out successfully");
                }

                case 16 -> {
                    logger.info("Application exited");
                    System.out.println("🚀 Thank you for using RevPlay");
                    System.exit(0);
                }

                // ================= ARTIST =================

                case 17 -> {
                    System.out.print("Artist Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    boolean success =
                            userService.registerArtist(email, password);

                    System.out.println(success
                            ? "✅ Artist registered successfully"
                            : "❌ Artist registration failed");
                }

                case 18 -> {
                    if (loggedInUser == null || !loggedInUser.isArtist()) {
                        System.out.println("❌ Artist login required");
                        break;
                    }

                    System.out.print("Song Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Artist Name: ");
                    String artistName = scanner.nextLine();

                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();

                    System.out.print("Duration (seconds): ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();

                    musicService.uploadSong(
                            title,
                            artistName,
                            genre,
                            duration,
                            loggedInUser.getEmail()
                    );
                }

                case 19 -> {
                    if (loggedInUser == null || !loggedInUser.isArtist()) {
                        System.out.println("❌ Artist login required");
                        break;
                    }

                    musicService.viewMySongStats(
                            loggedInUser.getEmail()
                    );
                }

                default -> System.out.println("❌ Invalid option");
            }
        }
    }
}
