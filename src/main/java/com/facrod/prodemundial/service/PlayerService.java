package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerDeleteDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.dto.PlayerUpdateDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.exceptions.OperationNotAllowedException;
import org.springframework.data.domain.Page;

public interface PlayerService {

    Page<PlayerResponseDTO> getPlayers(String sortBy, int page);

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
