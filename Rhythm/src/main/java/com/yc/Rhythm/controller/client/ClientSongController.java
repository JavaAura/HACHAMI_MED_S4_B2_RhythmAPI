package com.yc.Rhythm.controller.client;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.service.SongServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Client Song Controller", description = "Gestion des chansons par le client")
@RequestMapping("/api/users/songs")
public class ClientSongController {
    private final SongServiceImpl songService;
    public ClientSongController(SongServiceImpl songService){
        this.songService = songService;
    }


    /**
     * Get all songs
     * @return the list of songs
     */
    @Operation(summary = "Liste des chansons", description = "Récupère toutes les chansons avec pagination et tri")
    @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @GetMapping
    public ResponseEntity<Page<SongResponse>> getAllSongs(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<SongResponse> songs = songService.getAllSongs(pageable);
        return ResponseEntity.ok(songs);
    }

    /**
     * Search songs by title
     * @param title the title of the song
     * @return the list of songs
     */
    @Operation(summary = "Recherche de chansons par titre", description = "Recherche de chansons par titre avec pagination et tri")
    @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "404", description = "Aucune chanson trouvée", content = @Content(mediaType = "application/json", schema = @Schema()))
    @GetMapping("/searchByTitle")
    public ResponseEntity<Page<SongResponse>> searchByTitle(@Valid @RequestParam String title, @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<SongResponse> songs = songService.getAllSongsByTitle(title, pageable);
        return ResponseEntity.ok(songs);
    }

    /**
     * Search songs by album
     * @param album the album of the song
     * @return the list of songs
     */
    @Operation(summary = "Recherche de chansons par album", description = "Recherche de chansons par album avec pagination et tri")
    @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "404", description = "Aucune chanson trouvée", content = @Content(mediaType = "application/json", schema = @Schema()))
    @GetMapping("/searchByAlbum")
    public ResponseEntity<Page<SongResponse>> searchByAlbum(@Valid @RequestParam String album, @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<SongResponse> songs = songService.getAllSongsByAlbum(album, pageable);
        return ResponseEntity.ok(songs);
    }
}
