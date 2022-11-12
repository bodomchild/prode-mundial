package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.entity.dynamodb.MatchPrediction;
import com.facrod.prodemundial.exceptions.AppException;

import java.util.List;

public interface PredictionService {

    MatchPrediction save(String username, MatchPredictionCreateDTO prediction) throws AppException;

    MatchPrediction update(String username, Long matchId, MatchPredictionCreateDTO prediction) throws AppException;

    List<MatchPrediction> getAllByUsername(String username) throws AppException;

    MatchPrediction getByMatchId(String username, Long matchId) throws AppException;

}
