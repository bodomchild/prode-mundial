package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.MatchDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.MatchMapper;
import com.facrod.prodemundial.repository.MatchRepository;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.MatchService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("matchServiceAdmin")
@RequiredArgsConstructor
public class MatchServiceAdminImpl implements MatchService {

    private final Gson gson;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Override
    public MatchDTO getMatch(Long id) throws AppException {
        var match = matchRepository.findById(id).orElseThrow(() -> {
            log.error("Error al buscar partido con id '{}'", id);
            return new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");
        });
        return MatchMapper.toDto(match);
    }

    @Override
    public List<MatchDTO> getMatches() {
        return MatchMapper.toDto(matchRepository.findAll());
    }

    @Override
    public MatchDTO createMatch(MatchDTO match) throws AppException {
        if (matchRepository.existsById(match.getId())) {
            log.error("Error al crear partido con id '{}': ya existe", match.getId());
            throw new AppException(HttpStatus.CONFLICT, "El partido ya existe");
        }

        var entity = MatchMapper.toEntity(match);
        var homeTeam = teamRepository.findById(match.getHomeTeamId())
                .orElseThrow(() -> {
                    log.error("Error al crear partido con id '{}': equipo local con id '{}' no encontrado", match.getId(), match.getHomeTeamId());
                    return new AppException(HttpStatus.NOT_FOUND, "Equipo local '" + match.getHomeTeamId() + "' no encontrado");
                });
        var awayTeam = teamRepository.findById(match.getAwayTeamId())
                .orElseThrow(() -> {
                    log.error("Error al crear partido con id '{}': equipo visitante con id '{}' no encontrado", match.getId(), match.getAwayTeamId());
                    return new AppException(HttpStatus.NOT_FOUND, "Equipo visitante '" + match.getAwayTeamId() + "' no encontrado");
                });
        entity.setHomeTeam(homeTeam);
        entity.setAwayTeam(awayTeam);

        try {
            log.info("Creando partido: {}", gson.toJson(entity));
            entity = matchRepository.save(entity);
        } catch (Exception e) {
            log.error("Error al crear partido: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear partido");
        }

        return MatchMapper.toDto(entity);
    }

    @Override
    public MatchDTO updateMatch(MatchDTO match) throws AppException {
        // TODO: 4/4/22 implementar
        throw new AppException(HttpStatus.NOT_IMPLEMENTED, "No implementado");
    }

    @Override
    public void deleteMatch(Long id) throws AppException {
        if (!matchRepository.existsById(id)) {
            log.error("Error al eliminar partido con id '{}': no existe", id);
            throw new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");
        }

        try {
            log.info("Eliminando partido con id '{}'", id);
            matchRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar partido con id '{}': {}", id, e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar partido");
        }
    }

}
