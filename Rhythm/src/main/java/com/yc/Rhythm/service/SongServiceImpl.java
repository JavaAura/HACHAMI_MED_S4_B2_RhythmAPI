package com.yc.Rhythm.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.Rhythm.Mapper.SongMapper;
import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.entity.Album;
import com.yc.Rhythm.entity.Song;
import com.yc.Rhythm.repository.AlbumRepository;
import com.yc.Rhythm.repository.SongRepository;
import com.yc.Rhythm.service.Interfaces.ISongService;

@Service
public class SongServiceImpl implements ISongService {

    private final Logger logger = LoggerFactory.getLogger(SongServiceImpl.class);
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final SongMapper songMapper;
    private final GridFsService gridFsService;

    @Autowired
    public SongServiceImpl(final SongRepository songRepository, final AlbumRepository albumRepository, final SongMapper songMapper, final GridFsService gridFsService) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.songMapper = songMapper;
        this.gridFsService = gridFsService;
    }

    @Override
    @Transactional
    public SongResponse createSong(SongRequest request) throws IOException {
        logger.debug("Creating song with title: {}", request.getTitle());
        Song song = songMapper.toEntity(request);
        
        Album album = albumRepository.findById(request.getAlbumId())
        .orElseThrow(() -> new EntityNotFoundException("Album not found with id: " + request.getAlbumId()));
    
        song.setAlbum(album);

        if (request.getAudioFile() != null && !request.getAudioFile().isEmpty()) {
            try {
                String audioFileId = gridFsService.storeFile(request.getAudioFile());
                song.setAudioFileId(audioFileId);
                logger.debug("Stored audio file with ID: {}", audioFileId);
            } catch (IOException e) {
                logger.error("Failed to store audio file", e);
                throw new FileStorageException("Failed to store audio file", e);
            }
        }

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageId = gridFsService.storeFile(request.getImage());
                song.setImageId(imageId);
                logger.debug("Stored image file with ID: {}", imageId);
            } catch (IOException e) {
                logger.error("Failed to store image file", e);
                throw new FileStorageException("Failed to store image file", e);
            }
        }

        Song savedSong = songRepository.save(song);

        updateAlbumWithNewSong(album, savedSong);

        logger.info("Song created successfully with ID: {}", savedSong.getId());

        return songMapper.toResponse(savedSong);
    }

    @Override
    @Transactional
    public SongResponse updateSong(String id, SongRequest request) throws IOException {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        songMapper.updateEntityFromRequest(request, song);

        if (request.getAudioFile() != null && !request.getAudioFile().isEmpty()) {
            if (song.getAudioFileId() != null) {
                gridFsService.deleteFile(song.getAudioFileId());
            }
            String audioFileId = gridFsService.storeFile(request.getAudioFile());
            song.setAudioFileId(audioFileId);
        }

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            if (song.getImageId() != null) {
                gridFsService.deleteFile(song.getImageId());
            }
            String imageId = gridFsService.storeFile(request.getImage());
            song.setImageId(imageId);
        }

        Song updatedSong = songRepository.save(song);
        return songMapper.toResponse(updatedSong);
    }

    @Override
    @Transactional
    public void deleteSong(String id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (song.getAudioFileId() != null) {
            gridFsService.deleteFile(song.getAudioFileId());
        }

        if (song.getImageId() != null) {
            gridFsService.deleteFile(song.getImageId());
        }

        Album album = song.getAlbum();
        if (album != null) {
            album.getSongs().remove(song);
            albumRepository.save(album);
        }

        songRepository.delete(song);
    }

    @Override
    public Page<SongResponse> getAllSongs(Pageable pageable) {
        Page<Song> songPage = songRepository.findAll(pageable);
        return songPage.map(songMapper::toResponse);
    }

    @Override
    public SongResponse getSongById(String id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        return songMapper.toResponse(song);
    }

    @Override
    public Page<SongResponse> getSongsByAlbum(String albumId, Pageable pageable) {
        Page<Song> songPage = songRepository.findByAlbumId(albumId, pageable);
        return songPage.map(songMapper::toResponse);
    }

    @Override
    public Page<SongResponse> searchSongs(String query, Pageable pageable) {
        Page<Song> songPage = songRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(query, query, pageable);
        return songPage.map(songMapper::toResponse);
    }

    @Transactional
    public void updateAlbumSongList(String albumId, String songId) {
        Album album = albumRepository.findById(albumId)
            .orElseThrow(() -> new RuntimeException("Album not found"));
        Song song = songRepository.findById(songId)
            .orElseThrow(() -> new RuntimeException("Song not found"));
        
        if (!album.getSongs().contains(song)) {
            album.getSongs().add(song);
            albumRepository.save(album);
        }
    }

    private void updateAlbumWithNewSong(Album album, Song song) {
        album.getSongs().add(song);
        albumRepository.save(album);
    }

    public class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public class FileStorageException extends RuntimeException {
        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

