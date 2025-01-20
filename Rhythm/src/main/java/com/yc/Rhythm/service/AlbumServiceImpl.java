package com.yc.Rhythm.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.Rhythm.Mapper.AlbumMapper;
import com.yc.Rhythm.dto.req.AlbumRequest;
import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.entity.Album;
import com.yc.Rhythm.entity.Category;
import com.yc.Rhythm.entity.Song;
import com.yc.Rhythm.repository.AlbumRepository;
import com.yc.Rhythm.repository.CategoryRepository;
import com.yc.Rhythm.service.Interfaces.IAlbumService;

@Service
public class AlbumServiceImpl implements IAlbumService {

    private final Logger logger = LoggerFactory.getLogger(AlbumServiceImpl.class);
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final GridFsService gridFsService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper, GridFsService gridFsService, CategoryRepository categoryRepository) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.gridFsService = gridFsService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public AlbumResponse createAlbum(AlbumRequest request) throws IOException {
        Album album = albumMapper.toEntity(request);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
            album.setCategory(category);
        }else{
        logger.info("not foud");

        }

        if (request.getCoverImage() != null && !request.getCoverImage().isEmpty()) {
            String coverImageId = gridFsService.storeFile(request.getCoverImage());
            album.setCoverImageId(coverImageId);
        }

        Album savedAlbum = albumRepository.save(album);
        logger.info("Category name"+album.getCategory());

        return albumMapper.toResponse(savedAlbum);
    }

    @Override
    @Transactional
    public AlbumResponse updateAlbum(String id, AlbumRequest request) throws IOException {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        albumMapper.updateEntityFromRequest(request, album);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
            album.setCategory(category);
        }

        if (request.getCoverImage() != null && !request.getCoverImage().isEmpty()) {
            if (album.getCoverImageId() != null) {
                gridFsService.deleteFile(album.getCoverImageId());
            }
            String coverImageId = gridFsService.storeFile(request.getCoverImage());
            album.setCoverImageId(coverImageId);
        }

        Album updatedAlbum = albumRepository.save(album);
        return albumMapper.toResponse(updatedAlbum);
    }

    @Override
    @Transactional
    public void deleteAlbum(String id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        if (album.getCoverImageId() != null) {
            gridFsService.deleteFile(album.getCoverImageId());
        }

        albumRepository.delete(album);
    }

    @Override
    public Page<AlbumResponse> getAllAlbums(Pageable pageable) {
        Page<Album> albumPage = albumRepository.findAll(pageable);
        return albumPage.map(albumMapper::toResponse);
    }

    @Override
    public AlbumResponse getAlbumById(String id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
        return albumMapper.toResponse(album);
    }

    @Override
    public Album getAlbumEntityById(String id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
    }

    @Override
    public Page<AlbumResponse> searchAlbums(String query, Pageable pageable) {
        Page<Album> albumPage = albumRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(query, query, pageable);
        return albumPage.map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> getAlbumsByArtist(String artist, Pageable pageable) {
        Page<Album> albumPage = albumRepository.findByArtistContainingIgnoreCase(artist, pageable);
        return albumPage.map(albumMapper::toResponse);
    }

    @Override
    @Transactional
    public void addSongReferenceToAlbum(String albumId, String songId) {
        logger.debug("Adding song reference to album with ID: {}", albumId);
        Album album = albumRepository.findById(albumId)
            .orElseThrow(() -> new RuntimeException("Album not found"));
    
        if (!album.getSongs().stream().anyMatch(song -> song.getId().equals(songId))) {
            Song songReference = new Song();
            songReference.setId(songId);
            album.getSongs().add(songReference);
            albumRepository.save(album);
            logger.debug("Song reference added to album successfully");
        } else {
            logger.debug("Song reference already exists in the album");
        }
    }
}

