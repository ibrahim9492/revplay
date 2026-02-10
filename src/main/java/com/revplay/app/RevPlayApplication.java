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

        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService();
        MusicService musicService = new MusicService();
        PlaylistService playlistService = new PlaylistService();
        FavoriteService favoriteService = new FavoriteService();
        ListeningHistoryService historyService = new ListeningHistoryService();
        ArtistService artistService = new ArtistService();

        User loggedInUser = null;
        User loggedInArtist = null;

        while (true) {

            System.out.println("\n🎵 ===== RevPlay Menu ===== 🎵");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
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
            System.out.println("15. Logout User");
            System.out.println("16. Exit");
            System.out.println("17. Register Artist");
            System.out.println("18. Login Artist");
            System.out.println("19. Upload Song (Artist)");
            System.out.println("20. View My Song Statistics (Artist)");
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

                    userService.register(email, password, "USER");
                }

                case 2 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    loggedInUser = userService.login(email, password);
                }

                case 3 -> musicService.browseSongs();

                case 4 -> {
                    System.out.print("Keyword: ");
                    musicService.searchSongs(scanner.nextLine());
                }

                case 5 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    System.out.print("Song ID: ");
                    musicService.playSong(scanner.nextInt(), loggedInUser.getEmail());
                    scanner.nextLine();
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
                    System.out.print("Song ID: ");
                    favoriteService.addToFavorites(loggedInUser.getEmail(), scanner.nextInt());
                    scanner.nextLine();
                }

                case 9 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    favoriteService.viewFavoriteSongs(loggedInUser.getEmail());
                }

                case 10 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    System.out.print("Playlist Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Privacy (PUBLIC/PRIVATE): ");
                    String privacy = scanner.nextLine().toUpperCase();

                    playlistService.createPlaylist(
                            name, desc, privacy, loggedInUser.getEmail());
                }

                case 11 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }
                    playlistService.viewUserPlaylists(loggedInUser.getEmail());
                }

                case 12 -> {
                    if (loggedInUser == null) {
                        System.out.println("❌ Login required");
                        break;
                    }

                    System.out.print("Playlist ID: ");
                    int pid = scanner.nextInt();
                    System.out.print("Song ID: ");
                    int sid = scanner.nextInt();
                    scanner.nextLine();

                    playlistService.addSong(pid, sid, loggedInUser.getEmail());
                }

                case 13 -> {
                    System.out.print("Playlist ID: ");
                    int pid = scanner.nextInt();
                    System.out.print("Song ID: ");
                    int sid = scanner.nextInt();
                    scanner.nextLine();

                    playlistService.removeSong(pid, sid);
                }

                case 14 -> {
                    System.out.print("Playlist ID: ");
                    playlistService.deletePlaylist(scanner.nextInt());
                    scanner.nextLine();
                }

                case 15 -> {
                    loggedInUser = null;
                    System.out.println("👋 User logged out");
                }

                // ================= ARTIST =================

                case 17 -> {
                    System.out.print("Artist Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    artistService.registerArtist(email, password);
                }

                case 18 -> {
                    System.out.print("Artist Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    loggedInArtist = artistService.loginArtist(email, password);
                }

                case 19 -> {
                    if (loggedInArtist == null) {
                        System.out.println("❌ Artist login required");
                        break;
                    }

                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Duration (sec): ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();

                    artistService.uploadSong(
                            title,
                            loggedInArtist.getEmail(),
                            genre,
                            duration
                    );
                }

                case 20 -> {
                    if (loggedInArtist == null) {
                        System.out.println("❌ Artist login required");
                        break;
                    }
                    artistService.viewSongStats(loggedInArtist.getEmail());
                }

                // ================= EXIT =================

                case 16 -> {
                    System.out.println("🚀 Thank you for using RevPlay");
                    System.exit(0);
                }

                default -> System.out.println("❌ Invalid choice");
            }
        }
    }
}
