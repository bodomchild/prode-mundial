package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerDeleteDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.dto.PlayerUpdateDTO;
import com.facrod.prodemundial.entity.Player;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.mapper.PlayerMapper;
import com.facrod.prodemundial.pagination.Page;
import com.facrod.prodemundial.repository.PlayerRepository;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.PlayerService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service("playerServiceAdmin")
@RequiredArgsConstructor
public class PlayerServiceAdminImpl implements PlayerService {

    private static final String PLAYER_NOT_FOUND = "Jugador no encontrado";

    private final Gson gson;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    public Page<PlayerResponseDTO> getPlayers(String sortBy, int page) {
        // Nunca se va a llamar a este método
        return Page.<PlayerResponseDTO>builder()
                .data(Collections.emptyList())
                .sort(sortBy)
                .build();
    }

    @Override
    public PlayerResponseDTO createPlayer(PlayerCreateDTO player) throws AppException {
        var playerId = new Player.PlayerId(player.getId(), player.getTeamId());
        if (playerRepository.existsById(playerId)) {
            log.error("Error al crear jugador '{}' del equipo '{}' con número '{}': ya existe", player.getName(), player.getTeamId(), player.getId());
            throw new AppException(HttpStatus.CONFLICT, "El jugador ya existe");
        }

        if (!teamRepository.existsById(player.getTeamId())) {
            log.error("Error al crear jugador '{}' del equipo '{}' con número '{}': el equipo no existe", player.getName(), player.getTeamId(), player.getId());
            throw new AppException(HttpStatus.CONFLICT, "Equipo no encontrado");
        }

        var entity = PlayerMapper.toEntity(player);

        try {
            log.info("Creando jugador: {}", gson.toJson(entity));
            entity = playerRepository.save(entity);
        } catch (Exception e) {
            log.error("Error al crear jugador: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear jugador");
        }

        return PlayerMapper.toDto(entity);
    }

    @Override
    public void updatePlayer(PlayerCreateDTO player) throws AppException {
        var playerId = new Player.PlayerId(player.getId(), player.getTeamId());
        var entity = playerRepository.findById(playerId).orElseThrow(() -> {
            log.error("Error al actualizar jugador '{}' del equipo '{}' con número '{}': no existe", player.getName(), player.getTeamId(), player.getId());
            return new AppException(HttpStatus.NOT_FOUND, PLAYER_NOT_FOUND);
        });

        log.info("Actualizando jugador: {}", gson.toJson(entity));
        entity = PlayerMapper.toEntity(player);

        try {
            playerRepository.save(entity);
            log.info("Jugador actualizado: {}", gson.toJson(entity));
        } catch (Exception e) {
            log.error("Error al actualizar jugador: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar jugador");
        }
    }

    @Override
    public void updatePlayerGoalsOrCards(PlayerUpdateDTO player) throws AppException {
        var playerId = new Player.PlayerId(player.getId(), player.getTeamId());
        var entity = playerRepository.findById(playerId).orElseThrow(() -> {
            log.error("Error al actualizar goles o tarjetas del jugador del equipo '{}' con número '{}': no existe", player.getTeamId(), player.getId());
            return new AppException(HttpStatus.NOT_FOUND, PLAYER_NOT_FOUND);
        });

        var goals = Objects.requireNonNullElse(player.getGoals(), 0);
        var yellowCards = Objects.requireNonNullElse(player.getYellowCards(), 0);
        var redCards = Objects.requireNonNullElse(player.getRedCards(), 0);

        log.info("Actualizando goles o tarjetas del jugador: {}", gson.toJson(entity));
        entity.setGoals(entity.getGoals() + goals);
        entity.setYellowCards(entity.getYellowCards() + yellowCards);
        entity.setRedCards(entity.getRedCards() + redCards);

        try {
            playerRepository.save(entity);
            log.info("Jugador actualizado: {}", gson.toJson(entity));
        } catch (Exception e) {
            log.error("Error al actualizar jugador: {}", e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar jugador");
        }
    }

    @Override
    public void deletePlayer(PlayerDeleteDTO player) throws AppException {
        var playerId = new Player.PlayerId(player.getId(), player.getTeamId());
        if (!playerRepository.existsById(playerId)) {
            log.error("Error al eliminar jugador del equipo '{}' con número '{}': no existe", player.getTeamId(), player.getId());
            throw new AppException(HttpStatus.NOT_FOUND, PLAYER_NOT_FOUND);
        }

        try {
            log.info("Eliminando jugador del equipo '{}' con número '{}'", player.getTeamId(), player.getId());
            playerRepository.deleteById(playerId);
        } catch (Exception e) {
            log.error("Error al eliminar jugador del equipo '{}' con número '{}': {}", player.getTeamId(), player.getId(), e.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar jugador");
        }
    }

}
