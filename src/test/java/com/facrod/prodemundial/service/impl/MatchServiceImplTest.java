package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.dto.MatchUpdateResultDTO;
import com.facrod.prodemundial.dto.MatchUpdateStartTimeDTO;
import com.facrod.prodemundial.entity.WCMatch;
import com.facrod.prodemundial.entity.WCTeam;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.MatchRepository;
import com.facrod.prodemundial.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    private MatchService matchService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        matchService = new MatchServiceImpl(matchRepository);
    }

    @Test
    void getMatch_ok() throws AppException {
        var homeTeam = new WCTeam();
        homeTeam.setId("ARG");
        homeTeam.setName("Argentina");
        homeTeam.setWorldCupGroup("C");

        var awayTeam = new WCTeam();
        awayTeam.setId("KSA");
        awayTeam.setName("Arabia Saudita");
        awayTeam.setWorldCupGroup("C");

        var match = new WCMatch();
        match.setId(8L);
        match.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);

        var expected = new MatchResponseDTO();
        expected.setId(8L);
        expected.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        expected.setHomeTeam(MatchResponseDTO.TeamDTO.builder().id("ARG").name("Argentina").build());
        expected.setAwayTeam(MatchResponseDTO.TeamDTO.builder().id("KSA").name("Arabia Saudita").build());

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));

        var actual = matchService.getMatch(8L);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getMatch_notFound() {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        when(matchRepository.findById(8L)).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> matchService.getMatch(8L));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void getMatches() {
        var homeTeam = new WCTeam();
        homeTeam.setId("ARG");
        homeTeam.setName("Argentina");
        homeTeam.setWorldCupGroup("C");

        var awayTeam = new WCTeam();
        awayTeam.setId("KSA");
        awayTeam.setName("Arabia Saudita");
        awayTeam.setWorldCupGroup("C");

        var match = new WCMatch();
        match.setId(8L);
        match.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);

        var expectedMatch = new MatchResponseDTO();
        expectedMatch.setId(8L);
        expectedMatch.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        expectedMatch.setHomeTeam(MatchResponseDTO.TeamDTO.builder().id("ARG").name("Argentina").build());
        expectedMatch.setAwayTeam(MatchResponseDTO.TeamDTO.builder().id("KSA").name("Arabia Saudita").build());

        var expected = List.of(expectedMatch);

        when(matchRepository.findAll()).thenReturn(List.of(match));

        var actual = matchService.getMatches();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createMatch() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> matchService.createMatch(new MatchCreateDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchResult() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> matchService.updateMatchResult(new MatchUpdateResultDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchStartTime() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> matchService.updateMatchStartTime(new MatchUpdateStartTimeDTO()));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deleteMatch() {
        var expected = new AppException(HttpStatus.FORBIDDEN, "Operación no autorizada.");
        var actual = assertThrows(AppException.class, () -> matchService.deleteMatch(3L));
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

}