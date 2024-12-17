package com.yc.Rhythm.service.Interfaces;

import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISongService {
    SongResponse createSong(SongRequest request);
    SongResponse updateSong(String id, SongRequest request);
    boolean deleteSong(String id);
    Page<SongResponse> getAllSongs(Pageable pageable);
    Page<SongResponse> getAllSongsByTitle(String title, Pageable pageable);
    Page<SongResponse> getAllSongsByAlbum(String album, Pageable pageable);
}
