package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchResponseDTO>> getAll() {
        return ResponseEntity.ok(matchService.getMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> findById(@PathVariable("id") Long id) throws AppException {
        return ResponseEntity.ok(matchService.getMatch(id));
    }

}
