package com.revplay.model;

public class Playlist {

    private int playlistId;
    private String name;
    private String description;
    private String privacy;
    private String userEmail;

    public Playlist(int playlistId, String name,
                    String description, String privacy,
                    String userEmail) {
        this.playlistId = playlistId;
        this.name = name;
        this.description = description;
        this.privacy = privacy;
        this.userEmail = userEmail;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
