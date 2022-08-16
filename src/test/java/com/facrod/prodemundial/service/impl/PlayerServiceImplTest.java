package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerDeleteDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.dto.PlayerUpdateDTO;
import com.facrod.prodemundial.entity.Player;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.PlayerRepository;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        playerService = new PlayerServiceImpl(playerRepository, teamRepository);
    }

    @Test
    void getPlayers() {
        var player = new Player();
        player.setId(10);
        player.setTeamId("ARG");
        player.setName("Lionel Messi");
        player.setPosition("FW");
        player.setAge(35);

        var playerDto = new PlayerResponseDTO();
        playerDto.setId(10);
        playerDto.setTeamId("ARG");
        playerDto.setName("Lionel Messi");
        playerDto.setPosition("FW");
        playerDto.setAge(35);

        var expected = new PageImpl<>(List.of(playerDto));

        when(playerRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(player)));

        var actual = playerService.getPlayers("goals", 0);

        assertNotNull(actual);
        assertEquals(expected.getContent(), actual.getData());
    }

    @Test
    void getPlayersByTeam_ok() throws AppException {
        var teamId = "ARG";
        var player = new Player();
        player.setId(10);
        player.setTeamId("ARG");
        player.setName("Lionel Messi");
        player.setPosition("FW");
        player.setAge(35);

        var playerDto = new PlayerResponseDTO();
        playerDto.setId(10);
        playerDto.setTeamId("ARG");
        playerDto.setName("Lionel Messi");
        playerDto.setPosition("FW");
        playerDto.setAge(35);

        var expected = List.of(playerDto);

        when(teamRepository.existsById(teamId)).thenReturn(true);
        when(playerRepository.findByTeamId(teamId)).thenReturn(List.of(player));

        var actual = playerService.getPlayersByTeam(teamId);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getPlayersByTeam_teamNotFound() {
        var teamId = "ARG";

        var expected = new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");

        when(teamRepository.existsById(teamId)).thenReturn(false);

        var actual = assertThrows(AppException.class, () -> playerService.getPlayersByTeam(teamId));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void createPlayer() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> playerService.createPlayer(new PlayerCreateDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updatePlayer() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> playerService.updatePlayer(new PlayerCreateDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updatePlayerGoalsOrCards() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> playerService.updatePlayerGoalsOrCards(new PlayerUpdateDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deletePlayer() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> playerService.deletePlayer(new PlayerDeleteDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

}