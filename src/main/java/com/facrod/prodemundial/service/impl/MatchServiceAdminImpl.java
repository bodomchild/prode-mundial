package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.dto.MatchUpdateResultDTO;
import com.facrod.prodemundial.dto.MatchUpdateStartTimeDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.MatchMapper;
import com.facrod.prodemundial.mapper.PenaltiesRoundMapper;
import com.facrod.prodemundial.repository.MatchRepository;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.MatchService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service("matchServiceAdmin")
@RequiredArgsConstructor
public class MatchServiceAdminImpl implements MatchService {

    private static final String MATCH_NOT_FOUND = "Partido no encontrado";
    private final Gson gson;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Override
    public MatchResponseDTO getMatch(Long id) throws AppException {
        var match = matchRepository.findById(id).orElseThrow(() -> {
            log.error("Error al buscar partido con id '{}'", id);
            return new AppException(HttpStatus.NOT_FOUND, MATCH_NOT_FOUND);
        });
        return MatchMapper.toMatchResponseDto(match);
    }

    @Override
    public List<MatchResponseDTO> getMatches() {
        return MatchMapper.toMatchResponseDto(matchRepository.findAll());
    }

    @Override
    public MatchResponseDTO createMatch(MatchCreateDTO match) throws AppException {
        if (matchRepository.existsById(match.getId())) {
            log.error("Error al crear partido con id '{}': ya existe", match.getId());
            throw new AppException(HttpStatus.CONFLICT, "El partido ya existe");
        }

        var entity = MatchMapper.matchCreateDtoToEntity(match);
        var homeTeam = teamRepository.findById(match.getHomeTeamId()).orElseThrow(() -> {
            log.error("Error al crear partido con id '{}': equipo local con id '{}' no encontrado", match.getId(), match.getHomeTeamId());
            return new AppException(HttpStatus.NOT_FOUND, "Equipo local '" + match.getHomeTeamId() + "' no encontrado");
        });
        var awayTeam = teamRepository.findById(match.getAwayTeamId()).orElseThrow(() -> {
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

        return MatchMapper.toMatchResponseDto(entity);
    }

    @Override
    public void updateMatchResult(MatchUpdateResultDTO match) throws AppException {
        var entity = matchRepository.findById(match.getId()).orElseThrow(() -> {
            log.error("Error al actualizar partido con id '{}': no existe", match.getId());
            return new AppException(HttpStatus.NOT_FOUND, MATCH_NOT_FOUND);
        });

        if (entity.getStartTime().isAfter(LocalDateTime.now())) {
            throw new AppException(HttpStatus.CONFLICT,
                    "El partido todavía no se juega. Horario: " + entity.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        entity.setFinished(true);
        entity.setHomeScore(match.getHomeScore());
        entity.setAwayScore(match.getAwayScore());
        entity.setExtraTime(false);
        entity.setExtraTimeHomeScore(0);
        entity.setExtraTimeAwayScore(0);
        entity.setPenalties(false);

        if (Boolean.TRUE.equals(match.getExtraTime())) {
            entity.setExtraTime(true);
            entity.setExtraTimeHomeScore(match.getExtraTimeHomeScore());
            entity.setExtraTimeAwayScore(match.getExtraTimeAwayScore());
        }

        if (Boolean.TRUE.equals(match.getPenalties())) {
            entity.setPenalties(true);
            entity.setPenaltiesRound(PenaltiesRoundMapper.toEntity(match.getPenaltiesRound()));
        }

        try {
            log.info("Actualizando partido: {}", gson.toJson(entity));
            matchRepository.save(entity);
        } catch (Exception e) {
            log.error("Error al actualizar partido: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar partido");
        }
    }

    @Override
    public void updateMatchStartTime(MatchUpdateStartTimeDTO match) throws AppException {
        var entity = matchRepository.findById(match.getId()).orElseThrow(() -> {
            log.error("Error al actualizar horario del partido con id '{}': no existe", match.getId());
            return new AppException(HttpStatus.NOT_FOUND, MATCH_NOT_FOUND);
        });

        if (entity.isFinished()) {
            log.error("Error al actualizar horario del partido con id '{}': el partido ya se jugó", match.getId());
            throw new AppException(HttpStatus.CONFLICT, "El partido ya se jugó");
        }

        entity.setStartTime(match.getStartTime());
        try {
            log.info("Actualizando horario del partido: {}", gson.toJson(entity));
            matchRepository.save(entity);
        } catch (Exception e) {
            log.error("Error al actualizar horario del partido: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar horario del partido");
        }
    }

    @Override
    public void deleteMatch(Long id) throws AppException {
        if (!matchRepository.existsById(id)) {
            log.error("Error al eliminar partido con id '{}': no existe", id);
            throw new AppException(HttpStatus.NOT_FOUND, MATCH_NOT_FOUND);
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
