package com.yc.Rhythm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.yc.Rhythm.Mapper.AlbumMapper;
import com.yc.Rhythm.dto.req.AlbumRequest;
import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.entity.Album;
import com.yc.Rhythm.entity.Song;
import com.yc.Rhythm.entity.SongRepository;
import com.yc.Rhythm.repository.AlbumRepository;
import com.yc.Rhythm.service.Interfaces.IAlbumService;



@Service
public class AlbumServiceImpl implements IAlbumService {
    
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumServiceImpl(com.yc.Rhythm.repository.AlbumRepository albumRepository, SongRepository songRepository, AlbumMapper albumMapper){
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.albumMapper = albumMapper;
    }

    @Override
    public AlbumResponse createAlbum(AlbumRequest request) {
        Album album = albumMapper.toEntity(request);
        Album savedAlbum = albumRepository.save(album);
        return albumMapper.toResponse(savedAlbum);
    }

    @Override
    public AlbumResponse updateAlbum(String id, AlbumRequest request) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            throw new RuntimeException("Album non trouvé");
        }

        if(request.getTitle() != null) {
            album.get().setTitle(request.getTitle());
        }
        if(request.getArtist() != null) {
            album.get().setArtist(request.getArtist());
        }
        if (request.getReleaseYear() != null) {
            album.get().setReleaseYear(request.getReleaseYear());
        }
        if(request.getGenre() != null) {
            album.get().setGenre(request.getGenre());
        }
        Album savedAlbum = albumRepository.save(album.get());
       
        return albumMapper.toResponse(savedAlbum);
    }

    @Override
    public void deleteAlbum(String id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            throw new RuntimeException("Album non trouvé");
        }
        List<Song> songs = album.get().getSongs();
        if (songs != null) {
            for (Song song : songs) {
                songRepository.delete(song);
            }
        }
        
        albumRepository.delete(album.get());
    }



    // all users fonction
    @Override
    public Page<AlbumResponse> getAllAlbums(Pageable pageable) {
        Page<Album> albums = albumRepository.findAll(pageable);
        return albums.map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> getAlbumsByTitle(String title, Pageable pageable) {
        Page<Album> albums = albumRepository.findByTitleContaining(title, pageable);
        if (albums.isEmpty()) {
            throw new RuntimeException("Aucun album trouvé avec ce titre " + title);
        }
        return albums.map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> getAlbumsByArtist(String artist, Pageable pageable) {
        Page<Album> albums = albumRepository.findByArtistContaining(artist, pageable);
        if (albums.isEmpty()) {
            throw new RuntimeException("Aucun album trouvé de l'artiste: " + artist);
        }
        return albums.map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> filterAlbumsByYear(int startYear, int endYear, Pageable pageable) {
        Page<Album> albums = albumRepository.findByReleaseYearBetween(startYear, endYear, pageable);
        if (albums.isEmpty()) {
            throw new RuntimeException("Aucun album trouvé entre les années " + startYear + " et " + endYear);
        }
        return albums.map(albumMapper::toResponse);
    }


    // need it for song creation
    @Override
    public Album getAlbumById(String id) {
        return albumRepository.findById(id).orElseThrow(()->  new RuntimeException("Aucun album trouvé de l'album "));
    }

    @Override
    public Album updateAlbum(Album album) {
        return albumRepository.save(album);
    }
}
