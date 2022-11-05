package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.entity.Prediction;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/predictions")
public class PredictionController {

    private final PredictionService predictionService;

    @GetMapping
    public ResponseEntity<List<Prediction>> getByUsername(@RequestHeader(AUTHORIZATION) String authorization) throws AppException {
        var predictions = predictionService.getByUsername(authorization);
        return ResponseEntity.ok(predictions);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Prediction> getByUsernameAndMatchId(@RequestHeader(AUTHORIZATION) String authorization,
                                                              @PathVariable("matchId") String matchId) throws AppException {
        var prediction = predictionService.getByUsernameAndMatchId(authorization, matchId);
        return ResponseEntity.ok(prediction);
    }

}
