package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerDeleteDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.dto.PlayerUpdateDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.pagination.Page;
import com.facrod.prodemundial.service.PlayerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerService playerServiceAdmin;

    public PlayerController(PlayerService playerService, @Qualifier("playerServiceAdmin") PlayerService playerServiceAdmin) {
        this.playerService = playerService;
        this.playerServiceAdmin = playerServiceAdmin;
    }

    @GetMapping
    public ResponseEntity<Page<PlayerResponseDTO>> getPlayers(@RequestParam(value = "q", required = false) String sortBy,
                                                              @RequestParam(value = "p", defaultValue = "0") int page) {
        return ResponseEntity.ok(playerService.getPlayers(sortBy, page));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody @Valid PlayerCreateDTO player) throws AppException {
        var createdPlayer = playerServiceAdmin.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updatePlayer(@RequestBody @Valid PlayerCreateDTO player) throws AppException {
        playerServiceAdmin.updatePlayer(player);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updatePlayer(@RequestBody @Valid PlayerUpdateDTO player) throws AppException {
        playerServiceAdmin.updatePlayerGoalsOrCards(player);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlayer(@RequestBody @Valid PlayerDeleteDTO player) throws AppException {
        playerServiceAdmin.deletePlayer(player);
        return ResponseEntity.noContent().build();
    }

}
