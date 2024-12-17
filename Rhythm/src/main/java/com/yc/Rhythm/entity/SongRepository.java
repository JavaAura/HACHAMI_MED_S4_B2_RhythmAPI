package com.yc.Rhythm.entity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongRepository extends MongoRepository<Song, String> {
    List<Song> findByAlbumId(String albumId);

    
    Page<Song> findByTitleContaining(String title, Pageable pageable);
    Page<Song> findByAlbumId(String albumId, Pageable pageable);
}
