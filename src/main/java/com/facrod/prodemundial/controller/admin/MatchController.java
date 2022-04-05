package com.facrod.prodemundial.controller.admin;

import com.facrod.prodemundial.dto.MatchDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController("matchAdminController")
@RequestMapping("/api/v1/admin/match")
@Slf4j
public class MatchController {

    private final MatchService matchService;

    public MatchController(@Qualifier("matchServiceAdmin") MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@RequestBody @Valid MatchDTO match) throws AppException {
        var createdMatch = matchService.createMatch(match);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api/v1/match/{id}").build(createdMatch.getId());
        return ResponseEntity.created(uri).body(createdMatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) throws AppException {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

}
