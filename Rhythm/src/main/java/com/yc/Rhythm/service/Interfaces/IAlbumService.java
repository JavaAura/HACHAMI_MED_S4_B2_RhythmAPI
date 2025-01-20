package com.yc.Rhythm.service.Interfaces;

import com.yc.Rhythm.dto.req.AlbumRequest;
import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.entity.Album;
import com.yc.Rhythm.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IAlbumService {
    AlbumResponse createAlbum(AlbumRequest request) throws IOException;
    AlbumResponse updateAlbum(String id, AlbumRequest request) throws IOException;
    void deleteAlbum(String id);
    Page<AlbumResponse> getAllAlbums(Pageable pageable);
    AlbumResponse getAlbumById(String id);
    Album getAlbumEntityById(String id);
    Page<AlbumResponse> searchAlbums(String query, Pageable pageable);
    Page<AlbumResponse> getAlbumsByArtist(String artist, Pageable pageable);
    void addSongReferenceToAlbum(String albumId, String songId);
}

