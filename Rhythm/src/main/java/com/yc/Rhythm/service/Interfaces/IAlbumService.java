package com.yc.Rhythm.service.Interfaces;





import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yc.Rhythm.dto.req.AlbumRequest;
import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.entity.Album;


public interface IAlbumService {
  
    AlbumResponse createAlbum(AlbumRequest request);
    AlbumResponse updateAlbum(String id, AlbumRequest request);
    void deleteAlbum(String id);
    Page<AlbumResponse> getAllAlbums(Pageable pageable);
    Page<AlbumResponse> getAlbumsByTitle(String title, Pageable pageable);
    Page<AlbumResponse> getAlbumsByArtist(String artist, Pageable pageable);
    Page<AlbumResponse> filterAlbumsByYear(int startYear, int endYear, Pageable pageable);
    Album getAlbumById(String id);
    Album updateAlbum(Album album);
}