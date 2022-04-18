package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @Mock
    private TeamService teamServiceAdmin;

    private TeamController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);
        controller = new TeamController(teamService, teamServiceAdmin);
        var request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/teams");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void getAll() {
        var teams = List.of(createTeamDTO());
        var expected = ResponseEntity.ok(teams);

        when(teamService.getTeams()).thenReturn(teams);

        var actual = controller.getAll();

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void findById_ok() throws AppException {
        var team = createTeamDTO();
        var expected = ResponseEntity.ok(team);

        when(teamService.getTeam("ARG")).thenReturn(team);

        var actual = controller.findById("ARG");

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void findById_error() throws AppException {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");

        when(teamService.getTeam("ARG")).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.findById("ARG"));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createTeam_ok() throws AppException {
        var team = createTeamDTO();
        var location = URI.create("http://localhost/api/v1/teams/" + team.getId());
        var expected = ResponseEntity.created(location).body(team);

        when(teamServiceAdmin.createTeam(team)).thenReturn(team);

        var actual = controller.createTeam(team);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void createTeam_error() throws AppException {
        var team = createTeamDTO();
        var expected = new AppException(HttpStatus.CONFLICT, "El equipo ya existe");

        when(teamServiceAdmin.createTeam(team)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.createTeam(team));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deleteTeam_ok() throws AppException {
        var expected = ResponseEntity.noContent().build();

        doNothing().when(teamServiceAdmin).deleteTeam("ARG");

        var actual = controller.deleteTeam("ARG");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deleteTeam_error() throws AppException {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");

        doThrow(expected).when(teamServiceAdmin).deleteTeam("ARG");

        var actual = assertThrows(AppException.class, () -> controller.deleteTeam("ARG"));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private TeamDTO createTeamDTO() {
        var dto = new TeamDTO();
        dto.setId("ARG");
        dto.setName("Argentina");
        dto.setGroup("C");
        return dto;
    }

}