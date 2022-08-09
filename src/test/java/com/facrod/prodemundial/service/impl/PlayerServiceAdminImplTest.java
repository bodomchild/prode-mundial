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
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PlayerServiceAdminImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        playerService = new PlayerServiceAdminImpl(new Gson(), playerRepository, teamRepository);
    }

    @Test
    void getPlayers() {
        assertNull(playerService.getPlayers("goals", 0));
    }

    @Test
    void createPlayer_ok() throws AppException {
        var playerCreateDto = createPlayerCreateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var player = createPlayer();
        var expected = createPlayerResponseDTO();

        when(playerRepository.existsById(playerId)).thenReturn(false);
        when(teamRepository.existsById(playerCreateDto.getTeamId())).thenReturn(true);
        when(playerRepository.save(player)).thenReturn(player);

        var actual = playerService.createPlayer(playerCreateDto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createPlayer_playerExists() {
        var playerCreateDto = createPlayerCreateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var expected = new AppException(HttpStatus.CONFLICT, "El jugador ya existe");

        when(playerRepository.existsById(playerId)).thenReturn(true);

        var actual = assertThrows(AppException.class, () -> playerService.createPlayer(playerCreateDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void createPlayer_teamDoesntExist() {
        var playerCreateDto = createPlayerCreateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var expected = new AppException(HttpStatus.CONFLICT, "Equipo no encontrado");

        when(playerRepository.existsById(playerId)).thenReturn(false);
        when(teamRepository.existsById(playerCreateDto.getTeamId())).thenReturn(false);

        var actual = assertThrows(AppException.class, () -> playerService.createPlayer(playerCreateDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void createPlayer_saveError() {
        var playerCreateDto = createPlayerCreateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var player = createPlayer();
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear jugador");

        when(playerRepository.existsById(playerId)).thenReturn(false);
        when(teamRepository.existsById(playerCreateDto.getTeamId())).thenReturn(true);
        when(playerRepository.save(player)).thenThrow(new RuntimeException());

        var actual = assertThrows(AppException.class, () -> playerService.createPlayer(playerCreateDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updatePlayer_ok() throws AppException {
        var playerCreateDto = createPlayerCreateDTO();
        playerCreateDto.setName("Lionel Andres Messi");
        var playerId = new Player.PlayerId(10, "ARG");
        var player = createPlayer();
        var playerToSave = createPlayer();
        playerToSave.setName("Lionel Andres Messi");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.save(playerToSave)).thenReturn(playerToSave);

        playerService.updatePlayer(playerCreateDto);

        verify(playerRepository).findById(playerId);
        verify(playerRepository).save(playerToSave);
        verifyNoMoreInteractions(playerRepository);
    }

    @Test
    void updatePlayer_playerNotFound() {
        var playerCreateDto = createPlayerCreateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var expected = new AppException(HttpStatus.NOT_FOUND, "Jugador no encontrado");

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> playerService.updatePlayer(playerCreateDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updatePlayer_saveError() {
        var playerCreateDto = createPlayerCreateDTO();
        playerCreateDto.setName("Lionel Andres Messi");
        var playerId = new Player.PlayerId(10, "ARG");
        var player = createPlayer();
        var playerToSave = createPlayer();
        playerToSave.setName("Lionel Andres Messi");
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar jugador");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.save(playerToSave)).thenThrow(new RuntimeException());

        var actual = assertThrows(AppException.class, () -> playerService.updatePlayer(playerCreateDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updatePlayerGoalsOrCards_ok() throws AppException {
        var playerUpdateDto = createPlayerUpdateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var player = createPlayer();
        var playerToSave = createPlayer();
        playerToSave.setGoals(10);
        playerToSave.setYellowCards(2);
        playerToSave.setRedCards(1);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.save(playerToSave)).thenReturn(playerToSave);

        playerService.updatePlayerGoalsOrCards(playerUpdateDto);

        verify(playerRepository).findById(playerId);
        verify(playerRepository).save(playerToSave);
        verifyNoMoreInteractions(playerRepository);
    }

    @Test
    void updatePlayerGoalsOrCards_playerNotFound() {
        var playerUpdateDto = createPlayerUpdateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var expected = new AppException(HttpStatus.NOT_FOUND, "Jugador no encontrado");

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> playerService.updatePlayerGoalsOrCards(playerUpdateDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updatePlayerGoalsOrCards_saveError() {
        var playerUpdateDto = createPlayerUpdateDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var player = createPlayer();
        var playerToSave = createPlayer();
        playerToSave.setGoals(10);
        playerToSave.setYellowCards(2);
        playerToSave.setRedCards(1);
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar jugador");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.save(playerToSave)).thenThrow(new RuntimeException());

        var actual = assertThrows(AppException.class, () -> playerService.updatePlayerGoalsOrCards(playerUpdateDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deletePlayer_ok() throws AppException {
        var playerDeleteDto = createPlayerDeleteDTO();
        var playerId = new Player.PlayerId(10, "ARG");

        when(playerRepository.existsById(playerId)).thenReturn(true);
        doNothing().when(playerRepository).deleteById(playerId);

        playerService.deletePlayer(playerDeleteDto);

        verify(playerRepository).existsById(playerId);
        verify(playerRepository).deleteById(playerId);
        verifyNoMoreInteractions(playerRepository);
    }

    @Test
    void deletePlayer_playerNotFound() {
        var playerDeleteDto = createPlayerDeleteDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var expected = new AppException(HttpStatus.NOT_FOUND, "Jugador no encontrado");

        when(playerRepository.existsById(playerId)).thenReturn(false);

        var actual = assertThrows(AppException.class, () -> playerService.deletePlayer(playerDeleteDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deletePlayer_deleteError() {
        var playerDeleteDto = createPlayerDeleteDTO();
        var playerId = new Player.PlayerId(10, "ARG");
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar jugador");

        when(playerRepository.existsById(playerId)).thenReturn(true);
        doThrow(new RuntimeException()).when(playerRepository).deleteById(playerId);

        var actual = assertThrows(AppException.class, () -> playerService.deletePlayer(playerDeleteDto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    private PlayerCreateDTO createPlayerCreateDTO() {
        var player = new PlayerCreateDTO();
        player.setId(10);
        player.setTeamId("ARG");
        player.setName("Lionel Messi");
        player.setAge(35);
        player.setPosition("FW");
        return player;
    }

    private Player createPlayer() {
        var player = new Player();
        player.setId(10);
        player.setTeamId("ARG");
        player.setName("Lionel Messi");
        player.setAge(35);
        player.setPosition("FW");
        return player;
    }

    private PlayerResponseDTO createPlayerResponseDTO() {
        var player = new PlayerResponseDTO();
        player.setId(10);
        player.setTeamId("ARG");
        player.setName("Lionel Messi");
        player.setAge(35);
        player.setPosition("FW");
        return player;
    }

    private PlayerUpdateDTO createPlayerUpdateDTO() {
        var player = new PlayerUpdateDTO();
        player.setId(10);
        player.setTeamId("ARG");
        player.setGoals(10);
        player.setYellowCards(2);
        player.setRedCards(1);
        return player;
    }

    private PlayerDeleteDTO createPlayerDeleteDTO() {
        var player = new PlayerDeleteDTO();
        player.setId(10);
        player.setTeamId("ARG");
        return player;
    }

}