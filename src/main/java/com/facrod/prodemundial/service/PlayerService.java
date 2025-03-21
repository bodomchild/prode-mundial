package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerDeleteDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.dto.PlayerUpdateDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.exceptions.OperationNotAllowedException;
import com.facrod.prodemundial.pagination.Page;

import java.util.List;

public interface PlayerService {

    Page<PlayerResponseDTO> getPlayers(String sortBy, int page);

    List<PlayerResponseDTO> getPlayersByTeam(String teamId) throws AppException;

    default PlayerResponseDTO createPlayer(PlayerCreateDTO player) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void updatePlayer(PlayerCreateDTO player) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void updatePlayerGoalsOrCards(PlayerUpdateDTO player) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void deletePlayer(PlayerDeleteDTO player) throws AppException {
        throw new OperationNotAllowedException();
    }

}
