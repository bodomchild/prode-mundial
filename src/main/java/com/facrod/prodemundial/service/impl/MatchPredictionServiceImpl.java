package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.dto.MatchPredictionResponseDTO;
import com.facrod.prodemundial.dto.ScorerDTO;
import com.facrod.prodemundial.entity.dynamodb.MatchPrediction;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.MatchPredictionMapper;
import com.facrod.prodemundial.repository.MatchPredictionRepository;
import com.facrod.prodemundial.repository.MatchRepository;
import com.facrod.prodemundial.repository.PlayerRepository;
import com.facrod.prodemundial.repository.ProdeUserRepository;
import com.facrod.prodemundial.service.MatchPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchPredictionServiceImpl implements MatchPredictionService {

    // TODO: 11/19/2022 agregar logs
    private static final String USER_NOT_FOUND = "Usuario no encontrado";

    private final MatchPredictionRepository matchPredictionRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final ProdeUserRepository prodeUserRepository;

    @Override
    public MatchPredictionResponseDTO save(String username, MatchPredictionCreateDTO prediction) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        if (matchPredictionRepository.getByUsernameAndMatchId(username, prediction.getMatchId()) != null) {
            throw new AppException(HttpStatus.CONFLICT, "Ya existe una predicción para el partido");
        }

        var predictionEntity = MatchPredictionMapper.toEntity(prediction);
        predictionEntity.setUsername(username);

        var response = MatchPredictionMapper.toResponseDTO(matchPredictionRepository.save(predictionEntity));
        setScorers(predictionEntity, response);

        return response;
    }

    @Override
    public MatchPredictionResponseDTO update(String username, Long matchId, MatchPredictionCreateDTO prediction) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        if (matchPredictionRepository.getByUsernameAndMatchId(username, prediction.getMatchId()) == null) {
            throw new AppException(HttpStatus.NOT_FOUND, "No existe una predicción para el partido");
        }

        var predictionEntity = MatchPredictionMapper.toEntity(prediction);
        predictionEntity.setUsername(username);

        var response = MatchPredictionMapper.toResponseDTO(matchPredictionRepository.update(username, matchId, predictionEntity));
        setScorers(predictionEntity, response);

        return response;
    }

    @Override
    public List<MatchPredictionResponseDTO> getAllByUsername(String username) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        return matchPredictionRepository.getAllByUsername(username).stream()
                .map(prediction -> {
                    var response = MatchPredictionMapper.toResponseDTO(prediction);
                    setScorers(prediction, response);
                    return response;
                }).collect(Collectors.toList());
    }

    @Override
    public MatchPredictionResponseDTO getByMatchId(String username, Long matchId) throws AppException {
        if (!prodeUserRepository.existsByUsername(username)) {
            throw new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        }

        if (matchPredictionRepository.getByUsernameAndMatchId(username, matchId) == null) {
            throw new AppException(HttpStatus.NOT_FOUND, "No existe una predicción para el partido");
        }

        var prediction = matchPredictionRepository.getByUsernameAndMatchId(username, matchId);

        var response = MatchPredictionMapper.toResponseDTO(prediction);
        setScorers(prediction, response);

        return response;
    }

    private void setScorers(MatchPrediction prediction, MatchPredictionResponseDTO response) {
        var match = matchRepository.getReferenceById(prediction.getMatchId());

        if (prediction.getHomeTeamScorers() != null) {
            var homeTeamScorers = prediction.getHomeTeamScorers().stream()
                    .map(s -> {
                        var scorer = new ScorerDTO();
                        scorer.setId(s.getId());
                        scorer.setName(playerRepository.findNameByIdAndTeamId(s.getId(), match.getHomeTeam().getId()));
                        scorer.setGoals(s.getGoals());
                        return scorer;
                    }).collect(Collectors.toList());
            response.setHomeTeamScorers(homeTeamScorers);
        }

        if (prediction.getAwayTeamScorers() != null) {
            var awayTeamScorers = prediction.getAwayTeamScorers().stream()
                    .map(s -> {
                        var scorer = new ScorerDTO();
                        scorer.setId(s.getId());
                        scorer.setName(playerRepository.findNameByIdAndTeamId(s.getId(), match.getAwayTeam().getId()));
                        scorer.setGoals(s.getGoals());
                        return scorer;
                    }).collect(Collectors.toList());
            response.setAwayTeamScorers(awayTeamScorers);
        }
    }

}
