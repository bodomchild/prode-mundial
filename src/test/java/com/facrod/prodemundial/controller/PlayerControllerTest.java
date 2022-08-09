package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerDeleteDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.dto.PlayerUpdateDTO;
import com.facrod.prodemundial.enums.PlayerSort;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.pagination.Page;
import com.facrod.prodemundial.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private PlayerService playerServiceAdmin;

    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        playerController = new PlayerController(playerService, playerServiceAdmin);
        var request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/players");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void getPlayers() {
        var data = List.of(createPlayerResponseDTO());
        var page = Page.<PlayerResponseDTO>builder()
                .data(data)
                .sort(PlayerSort.GOALS.value())
                .pageNumber(0)
                .totalPages(1)
                .totalElements(1)
                .first(true)
                .last(true)
                .build();
        var expected = ResponseEntity.ok(page);

        when(playerService.getPlayers("goals", 0)).thenReturn(page);

        var actual = playerController.getPlayers("goals", 0);

        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void createPlayer() throws AppException {
        var playerCreateDTO = createPlayerCreateDTO();
        var player = createPlayerResponseDTO();
        var expected = ResponseEntity.status(HttpStatus.CREATED).body(player);

        when(playerServiceAdmin.createPlayer(playerCreateDTO)).thenReturn(player);

        var actual = playerController.createPlayer(playerCreateDTO);

        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void createPlayer_error() throws AppException {
        var playerCreateDTO = createPlayerCreateDTO();
        var expected = new AppException(HttpStatus.CONFLICT, "El jugador ya existe");

        when(playerServiceAdmin.createPlayer(playerCreateDTO)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> playerController.createPlayer(playerCreateDTO));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updatePlayer() throws AppException {
        var playerCreateDTO = createPlayerCreateDTO();
        var expected = ResponseEntity.noContent().build();

        doNothing().when(playerServiceAdmin).updatePlayer(playerCreateDTO);

        var actual = playerController.updatePlayer(playerCreateDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updatePlayer_error() throws AppException {
        var playerCreateDTO = createPlayerCreateDTO();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Jugador no encontrado");

        doThrow(expected).when(playerServiceAdmin).updatePlayer(playerCreateDTO);

        var actual = assertThrows(AppException.class, () -> playerController.updatePlayer(playerCreateDTO));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updatePlayerGoalsOrCards() throws AppException {
        var playerUpdateDTO = createPlayerUpdateDTO();
        var expected = ResponseEntity.noContent().build();

        doNothing().when(playerServiceAdmin).updatePlayerGoalsOrCards(playerUpdateDTO);

        var actual = playerController.updatePlayerGoalsOrCards(playerUpdateDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updatePlayerGoalsOrCards_error() throws AppException {
        var playerUpdateDTO = createPlayerUpdateDTO();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Jugador no encontrado");

        doThrow(expected).when(playerServiceAdmin).updatePlayerGoalsOrCards(playerUpdateDTO);

        var actual = assertThrows(AppException.class, () -> playerController.updatePlayerGoalsOrCards(playerUpdateDTO));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deletePlayer() throws AppException {
        var playerDeleteDTO = createPlayerDeleteDTO();
        var expected = ResponseEntity.noContent().build();

        doNothing().when(playerServiceAdmin).deletePlayer(playerDeleteDTO);

        var actual = playerController.deletePlayer(playerDeleteDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deletePlayer_error() throws AppException {
        var playerDeleteDTO = createPlayerDeleteDTO();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Jugador no encontrado");

        doThrow(expected).when(playerServiceAdmin).deletePlayer(playerDeleteDTO);

        var actual = assertThrows(AppException.class, () -> playerController.deletePlayer(playerDeleteDTO));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private PlayerResponseDTO createPlayerResponseDTO() {
        var player = new PlayerResponseDTO();
        player.setId(10);
        player.setTeamId("ARG");
        player.setName("Lionel Messi");
        player.setPosition("FW");
        player.setAge(35);
        player.setGoals(0);
        player.setYellowCards(0);
        player.setRedCards(0);
        return player;
    }

    private PlayerCreateDTO createPlayerCreateDTO() {
        var player = new PlayerCreateDTO();
        player.setTeamId("ARG");
        player.setName("Lionel Messi");
        player.setPosition("FW");
        player.setAge(35);
        return player;
    }

    private PlayerUpdateDTO createPlayerUpdateDTO() {
        var player = new PlayerUpdateDTO();
        player.setId(10);
        player.setTeamId("ARG");
        player.setGoals(1);
        player.setYellowCards(1);
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