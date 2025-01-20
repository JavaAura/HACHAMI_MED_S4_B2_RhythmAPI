package com.yc.Rhythm.controller.client;

import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.service.Interfaces.ISongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
@Tag(name = "Client Song", description = "Client APIs for song operations")
public class ClientSongController {

    private final ISongService songService;

    @Autowired
    public ClientSongController(ISongService songService) {
        this.songService = songService;
    }

    @GetMapping
    @Operation(summary = "Get all songs", description = "Retrieves a paginated list of all songs")
    @ApiResponse(responseCode = "200", description = "Songs retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<SongResponse>> getAllSongs(Pageable pageable) {
        Page<SongResponse> songs = songService.getAllSongs(pageable);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a song by ID", description = "Retrieves a song by its ID")
    @ApiResponse(responseCode = "200", description = "Song retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = SongResponse.class)))
    public ResponseEntity<SongResponse> getSongById(@PathVariable String id) {
        SongResponse song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/album/{albumId}")
    @Operation(summary = "Get songs by album", description = "Retrieves songs from a specific album")
    @ApiResponse(responseCode = "200", description = "Songs retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<SongResponse>> getSongsByAlbum(@PathVariable String albumId, Pageable pageable) {
        Page<SongResponse> songs = songService.getSongsByAlbum(albumId, pageable);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search")
    @Operation(summary = "Search songs", description = "Searches songs by title or artist")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<SongResponse>> searchSongs(@RequestParam String query, Pageable pageable) {
        Page<SongResponse> songs = songService.searchSongs(query, pageable);
        return ResponseEntity.ok(songs);
    }
}

