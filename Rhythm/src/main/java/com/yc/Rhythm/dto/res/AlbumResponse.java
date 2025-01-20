package com.yc.Rhythm.dto.res;

import java.util.List;

public class AlbumResponse {
    private String id;
    private String title;
    private String artist;
    private Integer releaseYear;
    private String coverImageId;
    private List<SongResponse> songs;
    private String categoryId;
    private String categoryName;

    // Getters and setters
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
    
    public Integer getReleaseYear() {
        return releaseYear;
    }
    
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
    
    public String getCoverImageId() {
        return coverImageId;
    }
    
    public void setCoverImageId(String coverImageId) {
        this.coverImageId = coverImageId;
    }
    
    public List<SongResponse> getSongs() {
        return songs;
    }
    
    public void setSongs(List<SongResponse> songs) {
        this.songs = songs;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

