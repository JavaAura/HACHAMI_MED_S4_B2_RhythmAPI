package com.yc.Rhythm.entity;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "albums")
public class Album {

    @Id    
    private String id;

    @NotBlank(message = "Le titre est obligatoire") 
    private String title;

    @NotBlank(message = "L'artiste est obligatoire")
    private String artist;

    @NotNull(message = "La date de sortie est obligatoire")
    @Min(value = 1980, message = "L'année de sortie doit être supérieure à 1980")
    @Max(value = 2024, message = "L'année de sortie doit être inférieure à 2024")
    private Integer releaseYear;

    @NotBlank(message = "Le genre est obligatoire")
    private String genre;

    @DBRef
    private List<Song> songs;

    // No-argument constructor
    public Album() {}

    // All-arguments constructor
    public Album(String id, String title, String artist, Integer releaseYear, String genre, List<Song> songs) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.songs = songs;
    }

    // Getter and setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for artist
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    // Getter and setter for releaseYear
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    // Getter and setter for genre
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Getter and setter for songs
    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
