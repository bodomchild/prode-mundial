package com.facrod.prodemundial.service;

import com.facrod.prodemundial.entity.Prediction;
import com.facrod.prodemundial.exceptions.AppException;

import java.util.List;

public interface PredictionService {

    Prediction getByUsernameAndMatchId(String authorizationHeader, String matchId) throws AppException;

    List<Prediction> getByUsername(String authorizationHeader) throws AppException;

}
