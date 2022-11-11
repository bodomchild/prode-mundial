package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.PredictionCreateDTO;
import com.facrod.prodemundial.entity.dynamodb.Prediction;

import java.util.List;

public interface PredictionService {

    Prediction save(String username, PredictionCreateDTO prediction);

    Prediction update(String username, Long matchId, PredictionCreateDTO prediction);

    List<Prediction> getAllByUsername(String username);

    Prediction getByMatchId(String username, Long matchId);

}
