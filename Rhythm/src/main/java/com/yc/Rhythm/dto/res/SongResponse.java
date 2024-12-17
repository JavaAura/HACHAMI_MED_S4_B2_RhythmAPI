package com.yc.Rhythm.dto.res;

public class SongResponse {
    private String title;
    private Integer duration;
    private Integer trackNumber;
    private AlbumSimpleResponse album;

    public SongResponse() {}

    public SongResponse(String title, Integer duration, Integer trackNumber, AlbumSimpleResponse album) {
        this.title = title;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.album = album;
    }

    // Getter and setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for duration
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    // Getter and setter for trackNumber
    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    // Getter and setter for album
    public AlbumSimpleResponse getAlbum() {
        return album;
    }

    public void setAlbum(AlbumSimpleResponse album) {
        this.album = album;
    }
}
