package com.yc.Rhythm.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yc.Rhythm.Mapper.SongMapper;
import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.entity.Album;
import com.yc.Rhythm.entity.Song;
import com.yc.Rhythm.entity.SongRepository;
import com.yc.Rhythm.service.Interfaces.ISongService;

@Service
public class SongServiceImpl implements ISongService {

    private AlbumServiceImpl albumService;
    private final SongRepository songRepository;
    private final  SongMapper songMapper;

    @Autowired
    public SongServiceImpl(AlbumServiceImpl albumService, SongRepository songRepository, SongMapper songMapper){
        this.albumService = albumService;
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    @Override
    public SongResponse createSong(SongRequest request) {
        Song song = songMapper.toEntity(request);
        Album album = albumService.getAlbumById(request.getAlbumId());
        
        song.setAlbum(album);
        List<Song> songs = album.getSongs();
        
        if (songs != null && songs.stream().anyMatch(s -> s.getTrackNumber() == request.getTrackNumber())) {
            throw new RuntimeException("Le numéro de piste est déjà utilisé");
        }
        Song savedSong = songRepository.save(song);
        songs.add(savedSong);
        album.setSongs(songs);
        albumService.updateAlbum(album);
        return songMapper.toResponse(savedSong);
    }

    @Override
    public SongResponse updateSong(String id, SongRequest request) {
        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Chanson non trouvée"));
        if (request.getTitle() == null && request.getDuration() == null && request.getTrackNumber() == null) {
            throw new RuntimeException("Aucune information fournie");
        }
        if (request.getTitle() != null) {
            song.setTitle(request.getTitle());
        }
        if (request.getDuration() != null) {
            song.setDuration(request.getDuration());
        }
        if (request.getTrackNumber() != null) {
            if(song.getTrackNumber()!=request.getTrackNumber()){
                Album album = albumService.getAlbumById(song.getAlbum().getId());
                List<Song> songs = album.getSongs();

                if(songs!=null && songs.stream().anyMatch(s -> s.getTrackNumber() == request.getTrackNumber())){
                    throw new RuntimeException("Le numéro de piste est déjà utilisé");
                }
            }
            song.setTrackNumber(request.getTrackNumber());
        }
        Song updatedSong = songRepository.save(song);
        return songMapper.toResponse(updatedSong);
    }

    @Override
    public boolean deleteSong(String id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Chanson non trouvée"));
        songRepository.delete(song);
        Album album = albumService.getAlbumById(song.getAlbum().getId());
        List<Song> songs = album.getSongs();
        songs.remove(song);
        album.setSongs(songs);
        albumService.updateAlbum(album);
        return true;
    }


    // client fonctions
    @Override
    public Page<SongResponse> getAllSongs(Pageable pageable) {
        Page<Song> songs = songRepository.findAll(pageable);
        return songs.map(songMapper::toResponse);
    }

    @Override
    public Page<SongResponse> getAllSongsByTitle(String title, Pageable pageable) {
        Page<Song> songs = songRepository.findByTitleContaining(title, pageable);
        if(songs.isEmpty()){
            throw new RuntimeException("Aucune chanson trouvée");
        }
        return songs.map(songMapper::toResponse);
    }


    @Override
    public Page<SongResponse> getAllSongsByAlbum(String album, Pageable pageable) {
        Page<Song> songs = songRepository.findByAlbumId(album, pageable);
        return songs.map(songMapper::toResponse);
    }
    
}
