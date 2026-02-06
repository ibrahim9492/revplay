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
            System.out.println("1. Register");
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
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            logger.info("Menu option selected: {}", choice);

            switch (choice) {

                // 1️⃣ REGISTER
                case 1 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    boolean success = userService.register(email, password, "USER");

                    if (success) {
                        System.out.println("✅ Registration successful");
                    } else {
                        System.out.println("❌ Registration failed");
                    }
                }

                // 2️⃣ LOGIN
                case 2 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    loggedInUser = userService.login(email, password);

                    if (loggedInUser != null) {
                        System.out.println("🎉 Login successful");
                    } else {
                        System.out.println("❌ Invalid credentials");
                    }
                }

                // 3️⃣ BROWSE SONGS
                case 3 -> musicService.browseSongs();

                // 4️⃣ SEARCH SONGS
                case 4 -> {
                    System.out.print("Enter keyword: ");
                    String keyword = scanner.nextLine();
                    musicService.searchSongs(keyword);
                }

                // 5️⃣ PLAY SONG
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

                // 6️⃣ RECENTLY PLAYED
                case 6 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    historyService.viewRecent(loggedInUser.getEmail());
                }

                // 7️⃣ LISTENING HISTORY
                case 7 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    historyService.viewHistory(loggedInUser.getEmail());
                }

                // 8️⃣ ADD TO FAVORITES
                case 8 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    System.out.print("Enter Song ID: ");
                    int songId = scanner.nextInt();
                    scanner.nextLine();

                    favoriteService.addToFavorites(loggedInUser.getEmail(), songId);
                }

                // 9️⃣ VIEW FAVORITES
                case 9 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    favoriteService.viewFavoriteSongs(loggedInUser.getEmail());
                }

                // 🔟 CREATE PLAYLIST
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

                // 1️⃣1️⃣ VIEW PLAYLISTS
                case 11 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    playlistService.viewUserPlaylists(loggedInUser.getEmail());
                }

                // 1️⃣2️⃣ ADD SONG TO PLAYLIST
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

                // 1️⃣3️⃣ REMOVE SONG FROM PLAYLIST
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

                // 1️⃣4️⃣ DELETE PLAYLIST
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

                // 1️⃣5️⃣ LOGOUT
                case 15 -> {
                    loggedInUser = null;
                    System.out.println("👋 Logged out successfully");
                }

                // 1️⃣6️⃣ EXIT
                case 16 -> {
                    logger.info("Application exited");
                    System.out.println("🚀 Thank you for using RevPlay");
                    System.exit(0);
                }

                default -> System.out.println("❌ Invalid option");
            }
        }
    }
}
