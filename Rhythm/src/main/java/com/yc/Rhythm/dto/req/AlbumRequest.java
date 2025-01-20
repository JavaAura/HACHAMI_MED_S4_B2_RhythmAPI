package com.yc.Rhythm.dto.req;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @Schema(description = "Album cover image file")
    private MultipartFile coverImage;

    @Schema(description = "Category ID")
    private String categoryId;

    // Constructor
    public AlbumRequest(String title, String artist, Integer releaseYear, MultipartFile coverImage, String categoryId) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.coverImage = coverImage;
        this.categoryId = categoryId;
    }

    // Getters and Setters
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
    
    public MultipartFile getCoverImage() {
        return coverImage;
    }
    
    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}

