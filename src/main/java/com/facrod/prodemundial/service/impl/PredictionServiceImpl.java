package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.PredictionCreateDTO;
import com.facrod.prodemundial.entity.dynamodb.Prediction;
import com.facrod.prodemundial.mapper.PredictionMapper;
import com.facrod.prodemundial.repository.PredictionRepository;
import com.facrod.prodemundial.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    // TODO: 28/8/22 controlar excepciones de no encontrado, no existente y esas cosas

    private final PredictionRepository predictionRepository;

    @Override
    public Prediction save(String username, PredictionCreateDTO prediction) {
        var predictionEntity = PredictionMapper.toEntity(prediction);
        predictionEntity.setUsername(username);
        return predictionRepository.save(predictionEntity);
    }

    @Override
    public Prediction update(String username, Long matchId, PredictionCreateDTO prediction) {
        var predictionEntity = PredictionMapper.toEntity(prediction);
        predictionEntity.setUsername(username);
        return predictionRepository.update(username, matchId, predictionEntity);
    }

    @Override
    public List<Prediction> getAllByUsername(String username) {
        return predictionRepository.getAllByUsername(username).stream()
                .map(prediction -> {
                    prediction.setUsername(null);
                    return prediction;
                }).collect(Collectors.toList());
    }

    @Override
    public Prediction getByMatchId(String username, Long matchId) {
        var prediction = predictionRepository.getByUsernameAndMatchId(username, matchId);
        prediction.setUsername(null);
        return prediction;
    }

}
