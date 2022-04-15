package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.dto.MatchUpdateResultDTO;
import com.facrod.prodemundial.dto.MatchUpdateStartTimeDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.exceptions.OperationNotAllowedException;

import java.util.List;

public interface MatchService {

    MatchResponseDTO getMatch(Long id) throws AppException;

    List<MatchResponseDTO> getMatches();

    default MatchResponseDTO createMatch(MatchCreateDTO match) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void updateMatchResult(MatchUpdateResultDTO match) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void updateMatchStartTime(MatchUpdateStartTimeDTO match) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void deleteMatch(Long id) throws AppException {
        throw new OperationNotAllowedException();
    }

}
