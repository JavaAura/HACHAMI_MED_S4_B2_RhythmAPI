package com.yc.Rhythm.controller.admin;

import com.yc.Rhythm.dto.req.AlbumRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/admin/albums")
@Tag(name = "Admin Album Management", description = "APIs for managing albums (Admin only)")
public class AdminAlbumController {

    private final IAlbumService albumService;

    @Autowired
    public AdminAlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new album", description = "Creates a new album with the given details")
    @ApiResponse(responseCode = "201", description = "Album created successfully", 
                 content = @Content(schema = @Schema(implementation = AlbumResponse.class)))
    public ResponseEntity<AlbumResponse> createAlbum(@Valid @ModelAttribute AlbumRequest albumRequest) throws IOException {
        AlbumResponse response = albumService.createAlbum(albumRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update an album", description = "Updates an existing album with the given details")
    @ApiResponse(responseCode = "200", description = "Album updated successfully", 
                 content = @Content(schema = @Schema(implementation = AlbumResponse.class)))
    public ResponseEntity<AlbumResponse> updateAlbum(
            @PathVariable String id,
            @Valid @ModelAttribute AlbumRequest albumRequest) throws IOException {
        AlbumResponse response = albumService.updateAlbum(id, albumRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an album", description = "Deletes an album by its ID")
    @ApiResponse(responseCode = "204", description = "Album deleted successfully")
    public ResponseEntity<Void> deleteAlbum(@PathVariable String id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
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
}

