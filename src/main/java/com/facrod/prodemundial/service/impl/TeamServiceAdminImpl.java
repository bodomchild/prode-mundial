package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.TeamMapper;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.TeamService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("teamServiceAdmin")
@RequiredArgsConstructor
public class TeamServiceAdminImpl implements TeamService {

    private final Gson gson;
    private final TeamRepository teamRepository;

    @Override
    public TeamDTO getTeam(String id) {
        // Nunca se va a llamar a este método
        return null;
    }

    @Override
    public List<TeamDTO> getTeams() {
        return new ArrayList<>();
    }

    @Override
    public TeamDTO createTeam(TeamDTO team) throws AppException {
        if (teamRepository.existsById(team.getId())) {
            log.error("Error al crear equipo con id '{}': ya existe", team.getId());
            throw new AppException(HttpStatus.CONFLICT, "El equipo ya existe");
        }

        var entity = TeamMapper.toEntity(team);

        try {
            log.info("Creando equipo: {}", gson.toJson(entity));
            entity = teamRepository.save(entity);
        } catch (Exception e) {
            log.error("Error al crear equipo: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear equipo");
        }

        return TeamMapper.toDto(entity);
    }

    @Override
    public void deleteTeam(String id) throws AppException {
        if (!teamRepository.existsById(id)) {
            log.error("Error al eliminar equipo con id '{}': no existe", id);
            throw new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");
        }

        try {
            log.info("Eliminando equipo con id '{}'", id);
            teamRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar equipo con id {}: {}", id, e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar equipo");
        }
    }

}
