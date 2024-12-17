package com.yc.Rhythm.dto.res;

public class SongSimpleResponse {
    private String title;
    private Integer duration;
    private Integer trackNumber;

    public SongSimpleResponse() {}

    public SongSimpleResponse(String title, Integer duration, Integer trackNumber) {
        this.title = title;
        this.duration = duration;
        this.trackNumber = trackNumber;
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
}
