package com.yc.Rhythm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "songs")
public class Song {

    @Id
    private String id;

    private String title;
    private String artist;
    private Integer duration;
    private Integer trackNumber;
    private String genre;
    private String audioFileId;
    private String imageId;

    @DBRef(lazy = true)
    private Album album;

    // Constructors
    public Song() {}

    public Song(String title, String artist, Integer duration, Integer trackNumber, String genre) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.genre = genre;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAudioFileId() {
        return audioFileId;
    }

    public void setAudioFileId(String audioFileId) {
        this.audioFileId = audioFileId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                ", trackNumber=" + trackNumber +
                ", genre='" + genre + '\'' +
                ", audioFileId='" + audioFileId + '\'' +
                ", imageId='" + imageId + '\'' +
                ", album=" + (album != null ? album.getId() : "null") +
                '}';
    }
}

