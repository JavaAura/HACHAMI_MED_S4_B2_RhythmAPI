package com.yc.Rhythm.controller.client;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.yc.Rhythm.dto.res.AlbumResponse;
import com.yc.Rhythm.service.GridFsService;
import com.yc.Rhythm.service.Interfaces.IAlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.media.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/albums")
@Tag(name = "Client Album", description = "Client APIs for album operations")
public class ClientAlbumController {

    private final IAlbumService albumService;
    private GridFsService gridFsTemplate;

    @Autowired
    public ClientAlbumController(IAlbumService albumService,GridFsService gridFsTemplate) {
        this.albumService = albumService;
        this.gridFsTemplate = gridFsTemplate;
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

    @GetMapping("/cover/{imageId}")
    public ResponseEntity<InputStreamResource> getAlbumCover(@PathVariable String imageId) {
        try {
            GridFSFile file = gridFsTemplate.getFile(imageId);
            if (file == null) {
                return ResponseEntity.notFound().build();
            }

            GridFsResource resource = gridFsTemplate.getResource(file);
            HttpHeaders headers = new HttpHeaders();
            
            // Get content type from GridFSFile metadata
            String contentType = file.getMetadata().get("_contentType").toString();
            headers.setContentType(MediaType.parseMediaType(contentType));
            
            headers.setContentLength(file.getLength());
            
            // Set caching headers
            headers.setCacheControl("public, max-age=31536000"); // Cache for 1 year
            
            // Use file.getFilename() as ETag if MD5 is not available
            headers.setETag("\"" + file.getFilename() + "\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(resource.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

