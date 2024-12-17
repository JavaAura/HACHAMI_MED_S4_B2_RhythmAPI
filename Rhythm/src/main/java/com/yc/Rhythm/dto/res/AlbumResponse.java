package com.yc.Rhythm.dto.res;

import java.util.List;

public class AlbumResponse {
    private String title;
    private String artist;
    private Integer releaseYear;
    private String genre;
    private List<SongSimpleResponse> songs;

    
    public AlbumResponse() {}

  
    public AlbumResponse(String title, String artist, Integer releaseYear, String genre, List<SongSimpleResponse> songs) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.songs = songs;
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

   
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

 
    public List<SongSimpleResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongSimpleResponse> songs) {
        this.songs = songs;
    }
}
