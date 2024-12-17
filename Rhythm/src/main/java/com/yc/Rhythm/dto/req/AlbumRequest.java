package com.yc.Rhythm.dto.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AlbumRequest {

    private String id;

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "L'artiste est obligatoire")
    private String artist;

    @Min(value = 1980, message = "L'année de sortie doit être supérieure à 1980")
    @Max(value = 2024, message = "L'année de sortie doit être inférieure à 2024")
    private Integer releaseYear;

    @NotBlank(message = "Le genre est obligatoire")
    private String genre;

    public AlbumRequest() {}

    public AlbumRequest(String id, String title, String artist, Integer releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

 
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
