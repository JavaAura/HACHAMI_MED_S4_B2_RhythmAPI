package com.yc.Rhythm.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.Rhythm.dto.req.SongRequest;
import com.yc.Rhythm.dto.res.SongResponse;
import com.yc.Rhythm.service.SongServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin/songs")
@Tag(name = "Admin Song Controller", description = "Gestion des chansons par l'administrateur")
public class AdminSongController {

    @Autowired
    private SongServiceImpl songService;

    /**
     * Create a new song
     * @param request The song creation request
     * @return The created song response
     */
    @Operation(
        summary = "Créer une nouvelle chanson",
        description = "Crée une nouvelle chanson avec les informations fournies"
    )
    @ApiResponse(responseCode = "201", description = "Chanson créée avec succès")
    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide - Erreurs de validation" ,
     content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "Validation failed: title: Title is required",
            "status": 400
        }
        """)))
    @PostMapping
    public ResponseEntity<SongResponse> createSong( @Valid @RequestBody SongRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(songService.createSong(request));
    }


    /***
     * Update a song
     * @param id The song ID
     * @param request The song update request
     * @return The updated song response
     */
    @Operation(
        summary = "Mettre à jour une chanson",
        description = "Met à jour une chanson avec les informations fournies"
    )
    @ApiResponse(responseCode = "200", description = "Chanson mise à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Chanson non trouvée")
    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> updateSong(@PathVariable String id,  @RequestBody SongRequest request) {
        return ResponseEntity.ok(songService.updateSong(id, request));
    }

    /***
     * Delete a song
     * @param id The song ID
     * @return The deleted song response
     */
    @Operation(
        summary = "Supprimer une chanson",
        description = "Supprime une chanson avec l'ID fourni"
    )
    @ApiResponse(responseCode = "200", description = "Chanson supprimée avec succès")
    @ApiResponse(responseCode = "404", description = "Chanson non trouvée")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable String id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    
}