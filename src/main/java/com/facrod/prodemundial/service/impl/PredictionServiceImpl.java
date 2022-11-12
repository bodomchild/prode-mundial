package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.entity.dynamodb.MatchPrediction;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.MatchPredictionMapper;
import com.facrod.prodemundial.repository.PredictionRepository;
import com.facrod.prodemundial.repository.ProdeUserRepository;
import com.facrod.prodemundial.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    // TODO: 28/8/22 controlar excepciones de no encontrado, no existente y esas cosas
    private static final String USER_NOT_FOUND = "Usuario no encontrado";

    private final PredictionRepository predictionRepository;
    private final ProdeUserRepository prodeUserRepository;

    @Override
    public MatchPrediction save(String username, MatchPredictionCreateDTO prediction) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        if (predictionRepository.getByUsernameAndMatchId(username, prediction.getMatchId()) != null) {
            throw new AppException(HttpStatus.CONFLICT, "Ya existe una predicción para el partido");
        }

        var predictionEntity = MatchPredictionMapper.toEntity(prediction);
        predictionEntity.setUsername(username);
        return predictionRepository.save(predictionEntity);
    }

    @Override
    public MatchPrediction update(String username, Long matchId, MatchPredictionCreateDTO prediction) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        if (predictionRepository.getByUsernameAndMatchId(username, prediction.getMatchId()) == null) {
            throw new AppException(HttpStatus.NOT_FOUND, "No existe una predicción para el partido");
        }

        var predictionEntity = MatchPredictionMapper.toEntity(prediction);
        predictionEntity.setUsername(username);
        return predictionRepository.update(username, matchId, predictionEntity);
    }

    @Override
    public List<MatchPrediction> getAllByUsername(String username) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        return predictionRepository.getAllByUsername(username).stream()
                .map(prediction -> {
                    prediction.setUsername(null);
                    return prediction;
                }).collect(Collectors.toList());
    }

    @Override
    public MatchPrediction getByMatchId(String username, Long matchId) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        var prediction = predictionRepository.getByUsernameAndMatchId(username, matchId);
        prediction.setUsername(null);
        return prediction;
    }

}
