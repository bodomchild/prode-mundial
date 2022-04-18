package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.dto.MatchUpdateResultDTO;
import com.facrod.prodemundial.dto.MatchUpdateStartTimeDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @Mock
    private MatchService matchServiceAdmin;

    private MatchController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);
        controller = new MatchController(matchService, matchServiceAdmin);
        var request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/matches");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void getAll() {
        var matches = List.of(createMatchResponseDTO());
        var expected = ResponseEntity.ok(matches);

        when(matchService.getMatches()).thenReturn(matches);

        var actual = controller.getAll();

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void findById_ok() throws AppException {
        var match = createMatchResponseDTO();
        var expected = ResponseEntity.ok(match);

        when(matchService.getMatch(8L)).thenReturn(match);

        var actual = controller.findById(8L);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void findById_error() throws AppException {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        when(matchService.getMatch(8L)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.findById(8L));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createMatch_ok() throws AppException {
        var requestBody = createMatchCreateDTO();
        var responseBody = createMatchResponseDTO();
        var location = URI.create("http://localhost/api/v1/matches/" + requestBody.getId());
        var expected = ResponseEntity.created(location).body(responseBody);

        when(matchServiceAdmin.createMatch(requestBody)).thenReturn(responseBody);

        var actual = controller.createMatch(requestBody);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void createMatch_error() throws AppException {
        var requestBody = createMatchCreateDTO();
        var expected = new AppException(HttpStatus.CONFLICT, "El partido ya existe");

        when(matchServiceAdmin.createMatch(requestBody)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.createMatch(requestBody));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateMatchResult_ok() throws AppException {
        var requestBody = createMatchUpdateResultDTO();
        var expected = ResponseEntity.noContent().build();

        doNothing().when(matchServiceAdmin).updateMatchResult(requestBody);

        var actual = controller.updateMatchResult(requestBody);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateMatchResult_error() throws AppException {
        var requestBody = createMatchUpdateResultDTO();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        doThrow(expected).when(matchServiceAdmin).updateMatchResult(requestBody);

        var actual = assertThrows(AppException.class, () -> controller.updateMatchResult(requestBody));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateMatchStartTime_ok() throws AppException {
        var requestBody = createMatchUpdateStartTimeDTO();
        var expected = ResponseEntity.noContent().build();

        doNothing().when(matchServiceAdmin).updateMatchStartTime(requestBody);

        var actual = controller.updateMatchStartTime(requestBody);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateMatchStartTime_error() throws AppException {
        var requestBody = createMatchUpdateStartTimeDTO();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        doThrow(expected).when(matchServiceAdmin).updateMatchStartTime(requestBody);

        var actual = assertThrows(AppException.class, () -> controller.updateMatchStartTime(requestBody));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deleteMatch() throws AppException {
        var expected = ResponseEntity.noContent().build();

        doNothing().when(matchServiceAdmin).deleteMatch(anyLong());

        var actual = controller.deleteMatch(8L);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deleteMatch_error() throws AppException {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        doThrow(expected).when(matchServiceAdmin).deleteMatch(anyLong());

        var actual = assertThrows(AppException.class, () -> controller.deleteMatch(8L));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private MatchCreateDTO createMatchCreateDTO() {
        var dto = new MatchCreateDTO();
        dto.setId(8L);
        dto.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        dto.setAwayTeamId("ARG");
        dto.setAwayTeamId("KSA");
        return dto;
    }

    private MatchUpdateResultDTO createMatchUpdateResultDTO() {
        var dto = new MatchUpdateResultDTO();
        dto.setId(8L);
        dto.setHomeScore(2);
        dto.setAwayScore(1);
        return dto;
    }

    private MatchUpdateStartTimeDTO createMatchUpdateStartTimeDTO() {
        var dto = new MatchUpdateStartTimeDTO();
        dto.setId(8L);
        dto.setStartTime(LocalDateTime.of(2022, 11, 22, 13, 0, 0));
        return dto;
    }

    private MatchResponseDTO createMatchResponseDTO() {
        var dto = new MatchResponseDTO();
        dto.setId(8L);
        dto.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        dto.setHomeTeam(MatchResponseDTO.TeamDTO.builder().id("ARG").name("Argentina").build());
        dto.setAwayTeam(MatchResponseDTO.TeamDTO.builder().id("KSA").name("Arabia Saudita").build());
        dto.setFinished(false);
        return dto;
    }

}