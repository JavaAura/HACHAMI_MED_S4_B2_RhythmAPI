package com.yc.Rhythm.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "songs")
public class Song {

    @Id
    private String id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    private String title;

    @PositiveOrZero(message = "La durée doit être positive ou zéro")
    private Integer duration;

    @PositiveOrZero(message = "Le numéro de piste doit être positif ou zéro")
    private Integer trackNumber;

    @NotNull(message = "Une chanson doit appartenir à un album")
    @DBRef
    private Album album;

    // No-argument constructor
    public Song() {}

    // All-arguments constructor
    public Song(String id, String title, Integer duration, Integer trackNumber, Album album) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.album = album;
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
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
