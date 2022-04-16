package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.dto.MatchUpdateResultDTO;
import com.facrod.prodemundial.dto.MatchUpdateStartTimeDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.MatchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchService matchServiceAdmin;

    public MatchController(MatchService matchService, @Qualifier("matchServiceAdmin") MatchService matchServiceAdmin) {
        this.matchService = matchService;
        this.matchServiceAdmin = matchServiceAdmin;
    }

    @GetMapping
    public ResponseEntity<List<MatchResponseDTO>> getAll() {
        return ResponseEntity.ok(matchService.getMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> findById(@PathVariable("id") Long id) throws AppException {
        return ResponseEntity.ok(matchService.getMatch(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody @Valid MatchCreateDTO match) throws AppException {
        var createdMatch = matchServiceAdmin.createMatch(match);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(createdMatch.getId());
        return ResponseEntity.created(uri).body(createdMatch);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateMatchResult(@RequestBody @Valid MatchUpdateResultDTO match) throws AppException {
        matchServiceAdmin.updateMatchResult(match);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateMatchStartTime(@RequestBody @Valid MatchUpdateStartTimeDTO match) throws AppException {
        matchServiceAdmin.updateMatchStartTime(match);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) throws AppException {
        matchServiceAdmin.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

}
