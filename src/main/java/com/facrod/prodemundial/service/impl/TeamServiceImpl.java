package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.TeamMapper;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Primary
@Service("teamService")
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamDTO getTeam(String id) throws AppException {
        var team = teamRepository.findById(id).orElseThrow(() -> {
            log.error("Error al buscar equipo con id '{}'", id);
            return new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");
        });
        return TeamMapper.toDto(team);
    }

    @Override
    public List<TeamDTO> getTeams() {
        return TeamMapper.toDto(teamRepository.findAll());
    }

}
