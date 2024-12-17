package com.yc.Rhythm.dto.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SongRequest {
    private String id;

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée doit être supérieure à 0")
    private Integer duration;

    @NotNull(message = "Le numéro de piste est obligatoire")
    @Min(value = 1, message = "Le numéro de piste doit être supérieur à 0")
    private Integer trackNumber;

    @NotNull(message = "L'ID de l'album est obligatoire")
    private String albumId;

    public SongRequest() {}

    public SongRequest(String id, String title, Integer duration, Integer trackNumber, String albumId) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.albumId = albumId;
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

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
