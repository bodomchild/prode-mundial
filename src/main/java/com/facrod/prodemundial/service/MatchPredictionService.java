package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.dto.MatchPredictionResponseDTO;
import com.facrod.prodemundial.exceptions.AppException;

import java.util.List;

public interface MatchPredictionService {

    MatchPredictionResponseDTO save(String username, MatchPredictionCreateDTO prediction) throws AppException;

    MatchPredictionResponseDTO update(String username, Long matchId, MatchPredictionCreateDTO prediction) throws AppException;

    List<MatchPredictionResponseDTO> getAllByUsername(String username) throws AppException;

    MatchPredictionResponseDTO getByMatchId(String username, Long matchId) throws AppException;

}
