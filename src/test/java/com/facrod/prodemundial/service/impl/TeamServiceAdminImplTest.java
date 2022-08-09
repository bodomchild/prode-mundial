package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.entity.WCTeam;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.TeamService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TeamServiceAdminImplTest {

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        teamService = new TeamServiceAdminImpl(new Gson(), teamRepository);
    }

    @Test
    void getTeam() throws AppException {
        assertNull(teamService.getTeam("ARG"));
    }

    @Test
    void getTeams() {
        var actual = teamService.getTeams();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void createTeam_ok() throws AppException {
        var dto = new TeamDTO();
        dto.setId("ARG");
        dto.setName("Argentina");
        dto.setGroup("C");
        var team = new WCTeam();
        team.setId("ARG");
        team.setName("Argentina");
        team.setWorldCupGroup("C");

        when(teamRepository.existsById("ARG")).thenReturn(false);
        when(teamRepository.save(team)).thenReturn(team);

        var actual = teamService.createTeam(dto);

        assertNotNull(actual);
        assertEquals(dto, actual);
    }

    @Test
    void createTeam_teamAlreadyExists() {
        var dto = new TeamDTO();
        dto.setId("ARG");
        dto.setName("Argentina");
        dto.setGroup("C");
        var expected = new AppException(HttpStatus.CONFLICT, "El equipo ya existe");

        when(teamRepository.existsById("ARG")).thenReturn(true);

        var actual = assertThrows(AppException.class, () -> teamService.createTeam(dto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void createTeam_saveException() {
        var dto = new TeamDTO();
        dto.setId("ARG");
        dto.setName("Argentina");
        dto.setGroup("C");
        var team = new WCTeam();
        team.setId("ARG");
        team.setName("Argentina");
        team.setWorldCupGroup("C");
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear equipo");

        when(teamRepository.existsById("ARG")).thenReturn(false);
        when(teamRepository.save(team)).thenThrow(new RuntimeException());

        var actual = assertThrows(AppException.class, () -> teamService.createTeam(dto));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deleteTeam_ok() throws AppException {
        when(teamRepository.existsById("ARG")).thenReturn(true);
        doNothing().when(teamRepository).deleteById("ARG");

        teamService.deleteTeam("ARG");

        verify(teamRepository).existsById("ARG");
        verify(teamRepository).deleteById("ARG");
        verifyNoMoreInteractions(teamRepository);
    }

    @Test
    void deleteTeam_notFound() {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Equipo no encontrado");

        when(teamRepository.existsById("ARG")).thenReturn(false);

        var actual = assertThrows(AppException.class, () -> teamService.deleteTeam("ARG"));

        verify(teamRepository).existsById("ARG");
        verifyNoMoreInteractions(teamRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deleteTeam_deleteException() {
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar equipo");

        when(teamRepository.existsById("ARG")).thenReturn(true);
        doThrow(new RuntimeException()).when(teamRepository).deleteById("ARG");

        var actual = assertThrows(AppException.class, () -> teamService.deleteTeam("ARG"));

        verify(teamRepository).existsById("ARG");
        verify(teamRepository).deleteById("ARG");
        verifyNoMoreInteractions(teamRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

}