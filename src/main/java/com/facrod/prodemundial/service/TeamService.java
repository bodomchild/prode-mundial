package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.exceptions.OperationNotAllowedException;

import java.util.List;

public interface TeamService {

    TeamDTO getTeam(String id) throws AppException;

    List<TeamDTO> getTeams();

    default TeamDTO createTeam(TeamDTO team) throws AppException {
        throw new OperationNotAllowedException();
    }

    default void deleteTeam(String id) throws AppException {
        throw new OperationNotAllowedException();
    }

    // TODO: 1/4/22 agregar operaciones de equipo (sumar puntos, goles, etc.)

}
