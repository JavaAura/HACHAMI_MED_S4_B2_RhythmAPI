package com.yc.Rhythm.controller.client;

import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.service.Interfaces.IAlbumService;
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
@RequestMapping("/api/albums")
@Tag(name = "Client Album", description = "Client APIs for album operations")
public class ClientAlbumController {

    private final IAlbumService albumService;

    @Autowired
    public ClientAlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    @Operation(summary = "Get all albums", description = "Retrieves a paginated list of all albums")
    @ApiResponse(responseCode = "200", description = "Albums retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<AlbumResponse>> getAllAlbums(Pageable pageable) {
        Page<AlbumResponse> albums = albumService.getAllAlbums(pageable);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an album by ID", description = "Retrieves an album by its ID")
    @ApiResponse(responseCode = "200", description = "Album retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = AlbumResponse.class)))
    public ResponseEntity<AlbumResponse> getAlbumById(@PathVariable String id) {
        AlbumResponse album = albumService.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/search")
    @Operation(summary = "Search albums", description = "Searches albums by title or artist")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<AlbumResponse>> searchAlbums(@RequestParam String query, Pageable pageable) {
        Page<AlbumResponse> albums = albumService.searchAlbums(query, pageable);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/artist/{artist}")
    @Operation(summary = "Get albums by artist", description = "Retrieves albums by a specific artist")
    @ApiResponse(responseCode = "200", description = "Albums retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<AlbumResponse>> getAlbumsByArtist(@PathVariable String artist, Pageable pageable) {
        Page<AlbumResponse> albums = albumService.getAlbumsByArtist(artist, pageable);
        return ResponseEntity.ok(albums);
    }
}

