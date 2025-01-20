package com.yc.Rhythm.service.Interfaces;

import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ISongService {
    SongResponse createSong(SongRequest request) throws IOException;
    SongResponse updateSong(String id, SongRequest request) throws IOException;
    void deleteSong(String id);
    Page<SongResponse> getAllSongs(Pageable pageable);
    SongResponse getSongById(String id);
    Page<SongResponse> getSongsByAlbum(String albumId, Pageable pageable);
    Page<SongResponse> searchSongs(String query, Pageable pageable);
}

