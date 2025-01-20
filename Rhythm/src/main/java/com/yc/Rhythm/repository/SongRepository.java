package com.yc.Rhythm.repository;

import com.yc.Rhythm.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {
    Page<Song> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(String title, String artist, Pageable pageable);
    Page<Song> findByAlbumId(String albumId, Pageable pageable);
    Page<Song> findByArtistContainingIgnoreCase(String artist, Pageable pageable);
}

