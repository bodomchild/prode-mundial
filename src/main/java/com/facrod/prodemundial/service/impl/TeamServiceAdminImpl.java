package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.TeamMapper;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("teamServiceAdmin")
@RequiredArgsConstructor
public class TeamServiceAdminImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamDTO getTeam(String id) throws AppException {
        var team = teamRepository.findById(id).orElseThrow(() -> {
            log.error("Error al buscar equipo con id {}", id);
            return new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");
        });
        return TeamMapper.toDto(team);
    }

    @Override
    public List<TeamDTO> getTeams() {
        var teams = teamRepository.findAllTeams();
        return TeamMapper.toDto(teams);
    }

    @Override
    public TeamDTO createTeam(TeamDTO team) throws AppException {
        var entity = TeamMapper.toEntity(team);

        try {
            log.info("Creando equipo: {}", entity);
            entity = teamRepository.save(entity);
        } catch (Exception e) {
            log.error("Error al crear equipo: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear equipo");
        }

        return TeamMapper.toDto(entity);
    }

    @Override
    public void deleteTeam(String id) throws AppException {
        try {
            teamRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al borrar equipo con id {}: {}", id, e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al borrar equipo");
        }
    }

}
