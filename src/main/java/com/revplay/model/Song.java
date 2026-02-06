package com.revplay.model;

public class Song {

    private int songId;
    private String title;
    private String artist;
    private String genre;
    private int duration;
    private int playCount;

    // No-args constructor (important for flexibility)
    public Song() {
    }

    // All-args constructor
    public Song(int songId, String title, String artist,
                String genre, int duration, int playCount) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.playCount = playCount;
    }

    // Getters
    public int getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public int getPlayCount() {
        return playCount;
    }

    // Setters
    public void setSongId(int songId) {
        this.songId = songId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    // Useful for logs & debugging
    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", playCount=" + playCount +
                '}';
    }
}
