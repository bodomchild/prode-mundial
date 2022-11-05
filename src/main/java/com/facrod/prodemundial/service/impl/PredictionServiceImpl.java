package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.entity.Prediction;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.PredictionRepository;
import com.facrod.prodemundial.security.JWTUtil;
import com.facrod.prodemundial.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final JWTUtil jwtUtil;
    private final PredictionRepository predictionRepository;

    @Override
    public Prediction getByUsernameAndMatchId(String authorizationHeader, String matchId) throws AppException {
        var token = authorizationHeader.substring(7);
        var username = jwtUtil.getUsernameFromToken(token);
        return predictionRepository.getByUsernameAndMatchId(username, Integer.parseInt(matchId));
    }

    @Override
    public List<Prediction> getByUsername(String authorizationHeader) throws AppException {
        var token = authorizationHeader.substring(7);
        var username = jwtUtil.getUsernameFromToken(token);
        return predictionRepository.getByUsername(username);
    }

}
