package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.dto.MatchPredictionResponseDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.MatchPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/predictions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MatchPredictionController {

    private final MatchPredictionService matchPredictionService;

    @PostMapping("/{username}")
    public ResponseEntity<MatchPredictionResponseDTO> savePrediction(@PathVariable("username") String username,
                                                                     @RequestBody @Valid MatchPredictionCreateDTO prediction) throws AppException {
        var response = matchPredictionService.save(username, prediction);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{matchId}").build(response.getMatchId());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{username}/{matchId}")
    public ResponseEntity<MatchPredictionResponseDTO> updatePrediction(@PathVariable("username") String username,
                                                                       @PathVariable("matchId") Long matchId,
                                                                       @RequestBody @Valid MatchPredictionCreateDTO prediction) throws AppException {
        var response = matchPredictionService.update(username, matchId, prediction);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok().location(uri).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<MatchPredictionResponseDTO>> getAll(@PathVariable("username") String username) throws AppException {
        var response = matchPredictionService.getAllByUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/{matchId}")
    public ResponseEntity<MatchPredictionResponseDTO> getPrediction(@PathVariable("username") String username,
                                                                    @PathVariable("matchId") Long matchId) throws AppException {
        var response = matchPredictionService.getByMatchId(username, matchId);
        return ResponseEntity.ok(response);
    }

}
