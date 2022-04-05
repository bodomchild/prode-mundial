package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.MatchDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.exceptions.OperationNotAllowedException;

import java.util.List;

public interface MatchService {

    MatchDTO getMatch(Long id) throws AppException;

    List<MatchDTO> getMatches();

    default MatchDTO createMatch(MatchDTO match) throws AppException {
        throw new OperationNotAllowedException();
    }

    default MatchDTO updateMatch(MatchDTO match) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void deleteMatch(Long id) throws AppException {
        throw new OperationNotAllowedException();
    }

}
