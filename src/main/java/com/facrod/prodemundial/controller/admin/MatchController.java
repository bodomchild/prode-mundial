package com.facrod.prodemundial.controller.admin;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.dto.MatchUpdateResultDTO;
import com.facrod.prodemundial.dto.MatchUpdateStartTimeDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController("matchAdminController")
@RequestMapping("/api/v1/admin/matches")
@Slf4j
public class MatchController {

    private final MatchService matchService;

    public MatchController(@Qualifier("matchServiceAdmin") MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody @Valid MatchCreateDTO match) throws AppException {
        var createdMatch = matchService.createMatch(match);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api/v1/match/{id}").build(createdMatch.getId());
        return ResponseEntity.created(uri).body(createdMatch);
    }

    @PutMapping
    public ResponseEntity<Void> updateMatchResult(@RequestBody @Valid MatchUpdateResultDTO match) throws AppException {
        matchService.updateMatchResult(match);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMatchStartTime(@RequestBody @Valid MatchUpdateStartTimeDTO match) throws AppException {
        matchService.updateMatchStartTime(match);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) throws AppException {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

}
