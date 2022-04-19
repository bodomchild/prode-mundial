package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.entity.WCTeam;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        teamService = new TeamServiceImpl(teamRepository);
    }

    @Test
    void getTeam_ok() throws AppException {
        var team = new WCTeam();
        team.setId("ARG");
        team.setName("Argentina");
        team.setWorldCupGroup("C");
        var expected = new TeamDTO();
        expected.setId("ARG");
        expected.setName("Argentina");
        expected.setGroup("C");

        when(teamRepository.findById("ARG")).thenReturn(Optional.of(team));

        var actual = teamService.getTeam("ARG");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getTeam_notFound() {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");

        when(teamRepository.findById("ARG")).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> teamService.getTeam("ARG"));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void getTeams() {
        var team = new WCTeam();
        team.setId("ARG");
        team.setName("Argentina");
        team.setWorldCupGroup("C");
        var dto = new TeamDTO();
        dto.setId("ARG");
        dto.setName("Argentina");
        dto.setGroup("C");
        var expected = List.of(dto);

        when(teamRepository.findAll()).thenReturn(List.of(team));

        var actual = teamService.getTeams();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createTeam() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> teamService.createTeam(new TeamDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deleteTeam() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> teamService.deleteTeam("ARG"));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

}