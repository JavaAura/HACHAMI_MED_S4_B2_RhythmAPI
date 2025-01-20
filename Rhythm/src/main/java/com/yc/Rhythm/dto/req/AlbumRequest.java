package com.yc.Rhythm.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class AlbumRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    @Schema(description = "Album title", example = "Thriller")
    private String title;

    @NotBlank(message = "Artist is required")
    @Size(max = 100, message = "Artist name must be less than 100 characters")
    @Schema(description = "Album artist", example = "Michael Jackson")
    private String artist;

    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be after 1900")
    @Max(value = 2100, message = "Release year must be before 2100")
    @Schema(description = "Album release year", example = "1982")
    private Integer releaseYear;

    @NotBlank(message = "Genre is required")
    @Size(max = 50, message = "Genre must be less than 50 characters")
    @Schema(description = "Album genre", example = "Pop")
    private String genre;

    @Schema(description = "Album cover image file")
    private MultipartFile coverImage;

    // Constructor
    public AlbumRequest(String title, String artist, Integer releaseYear, String genre, MultipartFile coverImage) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.coverImage = coverImage;
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

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
}

