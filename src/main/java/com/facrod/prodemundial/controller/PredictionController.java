package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.entity.dynamodb.MatchPrediction;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.PredictionService;
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
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/{username}")
    public ResponseEntity<MatchPrediction> savePrediction(@PathVariable("username") String username,
                                                          @RequestBody @Valid MatchPredictionCreateDTO prediction) throws AppException {
        var response = predictionService.save(username, prediction);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{matchId}").build(response.getMatchId());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{username}/{matchId}")
    public ResponseEntity<MatchPrediction> updatePrediction(@PathVariable("username") String username,
                                                            @PathVariable("matchId") Long matchId,
                                                            @RequestBody @Valid MatchPredictionCreateDTO prediction) throws AppException {
        var response = predictionService.update(username, matchId, prediction);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok().location(uri).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<MatchPrediction>> getAll(@PathVariable("username") String username) throws AppException {
        var response = predictionService.getAllByUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/{matchId}")
    public ResponseEntity<MatchPrediction> getPrediction(@PathVariable("username") String username,
                                                         @PathVariable("matchId") Long matchId) throws AppException {
        var response = predictionService.getByMatchId(username, matchId);
        return ResponseEntity.ok(response);
    }

}
