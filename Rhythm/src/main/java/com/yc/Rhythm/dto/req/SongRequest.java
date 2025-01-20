package com.yc.Rhythm.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class SongRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    @Schema(description = "Song title", example = "Your Song Title")
    private String title;

    @NotBlank(message = "Artist is required")
    @Size(max = 100, message = "Artist name must be less than 100 characters")
    @Schema(description = "Song artist", example = "Artist Name")
    private String artist;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 second")
    @Schema(description = "Song duration in seconds", example = "180")
    private Integer duration;

    @NotNull(message = "Track number is required")
    @Min(value = 1, message = "Track number must be at least 1")
    @Schema(description = "Track number on the album", example = "1")
    private Integer trackNumber;

    @NotBlank(message = "Genre is required")
    @Size(max = 50, message = "Genre must be less than 50 characters")
    @Schema(description = "Song genre", example = "Pop")
    private String genre;

    @NotBlank(message = "Album ID is required")
    @Schema(description = "ID of the album this song belongs to", example = "678d22ab3986e91c94c68055")
    private String albumId;

    @Schema(description = "Audio file of the song")
    private MultipartFile audioFile;

    @Schema(description = "Image file for the song (if different from album cover)")
    private MultipartFile image;

    // Default constructor
    public SongRequest() {}

    // Constructor with all fields
    public SongRequest(String title, String artist, Integer duration, Integer trackNumber, String genre, String albumId, MultipartFile audioFile, MultipartFile image) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.genre = genre;
        this.albumId = albumId;
        this.audioFile = audioFile;
        this.image = image;
    }

    // Getters and setters
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

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public MultipartFile getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(MultipartFile audioFile) {
        this.audioFile = audioFile;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}

