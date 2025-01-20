package com.yc.Rhythm.controller.admin;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.service.Interfaces.ISongService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/songs")
@Tag(name = "Admin Song Management", description = "APIs for managing songs (Admin only)")
public class AdminSongController {

    private final Logger logger = LoggerFactory.getLogger(AdminSongController.class);
    private final ISongService songService;

    @Autowired
    public AdminSongController(ISongService songService) {
        this.songService = songService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new song", description = "Creates a new song with the given details")
    @ApiResponse(responseCode = "201", description = "Song created successfully", 
                 content = @Content(schema = @Schema(implementation = SongResponse.class)))
    public ResponseEntity<?> createSong(@Valid @ModelAttribute SongRequest songRequest) {
        try {
            SongResponse response = songService.createSong(songRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("Error creating song: ", e);
            return new ResponseEntity<>("Error creating song: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

