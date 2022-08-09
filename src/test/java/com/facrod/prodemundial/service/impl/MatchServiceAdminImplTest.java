package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.*;
import com.facrod.prodemundial.entity.WCMatch;
import com.facrod.prodemundial.entity.WCPenaltiesRound;
import com.facrod.prodemundial.entity.WCPenalty;
import com.facrod.prodemundial.entity.WCTeam;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.MatchRepository;
import com.facrod.prodemundial.repository.TeamRepository;
import com.facrod.prodemundial.service.MatchService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MatchServiceAdminImplTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TeamRepository teamRepository;

    private MatchService matchService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        matchService = new MatchServiceAdminImpl(new Gson(), matchRepository, teamRepository);
    }

    @Test
    void getMatch() throws AppException {
        assertNull(matchService.getMatch(8L));
    }

    @Test
    void getMatches() {
        var actual = matchService.getMatches();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void createMatch_ok() throws AppException {
        var startTime = LocalDateTime.now().minusSeconds(1L);
        var matchCreate = matchCreate();
        matchCreate.setStartTime(startTime);
        var match = match();
        match.setStartTime(startTime);
        var expected = matchResponse();
        expected.setStartTime(startTime);

        when(matchRepository.existsById(8L)).thenReturn(false);
        when(teamRepository.findById("ARG")).thenReturn(Optional.of(homeTeam()));
        when(teamRepository.findById("KSA")).thenReturn(Optional.of(awayTeam()));
        when(matchRepository.save(match)).thenReturn(match);

        var actual = matchService.createMatch(matchCreate);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createMatch_alreadyExists() {
        var matchCreate = matchCreate();
        var expected = new AppException(HttpStatus.CONFLICT, "El partido ya existe");

        when(matchRepository.existsById(8L)).thenReturn(true);

        var actual = assertThrows(AppException.class, () -> matchService.createMatch(matchCreate));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void createMatch_homeTeamNotFound() {
        var matchCreate = matchCreate();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Equipo local 'ARG' no encontrado");

        when(matchRepository.existsById(8L)).thenReturn(false);
        when(teamRepository.findById("ARG")).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> matchService.createMatch(matchCreate));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void createMatch_awayTeamNotFound() {
        var matchCreate = matchCreate();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Equipo visitante 'KSA' no encontrado");

        when(matchRepository.existsById(8L)).thenReturn(false);
        when(teamRepository.findById("ARG")).thenReturn(Optional.of(homeTeam()));
        when(teamRepository.findById("KSA")).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> matchService.createMatch(matchCreate));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void createMatch_saveException() {
        var matchCreate = matchCreate();
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear partido");

        when(matchRepository.existsById(8L)).thenReturn(false);
        when(teamRepository.findById("ARG")).thenReturn(Optional.of(homeTeam()));
        when(teamRepository.findById("KSA")).thenReturn(Optional.of(awayTeam()));
        when(matchRepository.save(match())).thenThrow(new RuntimeException());

        var actual = assertThrows(AppException.class, () -> matchService.createMatch(matchCreate));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchResult_ok() throws AppException {
        var matchUpdate = matchUpdateResult();
        var match = match();
        match.setStartTime(LocalDateTime.now());

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));
        when(matchRepository.save(match)).thenReturn(match);

        matchService.updateMatchResult(matchUpdate);

        verify(matchRepository).findById(8L);
        verify(matchRepository).save(match);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void updateMatchResult_okExtraTime() throws AppException {
        var matchUpdate = matchUpdateResult();
        matchUpdate.setAwayScore(3);
        matchUpdate.setExtraTime(true);
        matchUpdate.setExtraTimeHomeScore(2);
        matchUpdate.setExtraTimeAwayScore(1);
        var match = match();
        match.setStartTime(LocalDateTime.now());
        var matchToSave = match();
        matchToSave.setExtraTime(true);
        matchToSave.setExtraTimeHomeScore(2);
        matchToSave.setExtraTimeAwayScore(1);

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));
        when(matchRepository.save(matchToSave)).thenReturn(matchToSave);

        matchService.updateMatchResult(matchUpdate);

        verify(matchRepository).findById(8L);
        verify(matchRepository).save(match);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void updateMatchResult_okPenalties() throws AppException {
        var matchUpdate = matchUpdateResult();
        matchUpdate.setAwayScore(3);
        matchUpdate.setExtraTime(true);
        matchUpdate.setExtraTimeHomeScore(0);
        matchUpdate.setExtraTimeAwayScore(0);
        matchUpdate.setPenalties(true);
        matchUpdate.setPenaltiesRound(createPenaltiesRoundDto());
        var match = match();
        match.setStartTime(LocalDateTime.now());
        var matchToSave = match();
        matchToSave.setExtraTime(true);
        matchToSave.setExtraTimeHomeScore(0);
        matchToSave.setExtraTimeAwayScore(0);
        matchToSave.setPenalties(true);
        matchToSave.setPenaltiesRound(createPenaltiesRoundEntity());

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));
        when(matchRepository.save(matchToSave)).thenReturn(matchToSave);

        matchService.updateMatchResult(matchUpdate);

        verify(matchRepository).findById(8L);
        verify(matchRepository).save(match);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void updateMatchResult_matchNotExists() {
        var matchUpdate = matchUpdateResult();
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        when(matchRepository.findById(8L)).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> matchService.updateMatchResult(matchUpdate));

        verify(matchRepository).findById(8L);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchResult_matchNotPlayed() {
        var matchUpdate = matchUpdateResult();
        var match = match();
        var expected = new AppException(HttpStatus.CONFLICT,
                "El partido todavía no se juega. Horario: " + match.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));

        var actual = assertThrows(AppException.class, () -> matchService.updateMatchResult(matchUpdate));

        verify(matchRepository).findById(8L);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchResult_saveException() {
        var matchUpdate = matchUpdateResult();
        var match = match();
        match.setStartTime(LocalDateTime.now());
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar partido");

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));
        when(matchRepository.save(match)).thenThrow(new RuntimeException());

        var actual = assertThrows(AppException.class, () -> matchService.updateMatchResult(matchUpdate));

        verify(matchRepository).findById(8L);
        verify(matchRepository).save(match);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchStartTime_ok() throws AppException {
        var matchUpdate = new MatchUpdateStartTimeDTO();
        matchUpdate.setId(8L);
        matchUpdate.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        var match = match();
        match.setStartTime(LocalDateTime.now());
        var matchToSave = match();
        matchToSave.setStartTime(matchUpdate.getStartTime());

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));
        when(matchRepository.save(matchToSave)).thenReturn(matchToSave);

        matchService.updateMatchStartTime(matchUpdate);

        verify(matchRepository).findById(8L);
        verify(matchRepository).save(matchToSave);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void updateMatchStartTime_matchNotExists() {
        var matchUpdate = new MatchUpdateStartTimeDTO();
        matchUpdate.setId(8L);
        matchUpdate.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        when(matchRepository.findById(8L)).thenReturn(Optional.empty());

        var actual = assertThrows(AppException.class, () -> matchService.updateMatchStartTime(matchUpdate));

        verify(matchRepository).findById(8L);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchStartTime_matchAlreadyPlayed() {
        var matchUpdate = new MatchUpdateStartTimeDTO();
        matchUpdate.setId(8L);
        matchUpdate.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        var match = match();
        match.setFinished(true);
        var expected = new AppException(HttpStatus.CONFLICT, "El partido ya se jugó");

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));

        var actual = assertThrows(AppException.class, () -> matchService.updateMatchStartTime(matchUpdate));

        verify(matchRepository).findById(8L);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void updateMatchStartTime_saveException() {
        var matchUpdate = new MatchUpdateStartTimeDTO();
        matchUpdate.setId(8L);
        matchUpdate.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        var match = match();
        match.setStartTime(LocalDateTime.now());
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar horario del partido");

        when(matchRepository.findById(8L)).thenReturn(Optional.of(match));
        doThrow(new RuntimeException()).when(matchRepository).save(match);

        var actual = assertThrows(AppException.class, () -> matchService.updateMatchStartTime(matchUpdate));

        verify(matchRepository).findById(8L);
        verify(matchRepository).save(match);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deleteMatch_ok() throws AppException {
        when(matchRepository.existsById(8L)).thenReturn(true);
        doNothing().when(matchRepository).deleteById(8L);

        matchService.deleteMatch(8L);

        verify(matchRepository).existsById(8L);
        verify(matchRepository).deleteById(8L);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void deleteMatch_matchNotExists() {
        var expected = new AppException(HttpStatus.NOT_FOUND, "Partido no encontrado");

        when(matchRepository.existsById(8L)).thenReturn(false);

        var actual = assertThrows(AppException.class, () -> matchService.deleteMatch(8L));

        verify(matchRepository).existsById(8L);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void deleteMatch_deleteException() {
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar partido");

        when(matchRepository.existsById(8L)).thenReturn(true);
        doThrow(new RuntimeException()).when(matchRepository).deleteById(8L);

        var actual = assertThrows(AppException.class, () -> matchService.deleteMatch(8L));

        verify(matchRepository).existsById(8L);
        verify(matchRepository).deleteById(8L);
        verifyNoMoreInteractions(matchRepository);
        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    private WCTeam homeTeam() {
        var homeTeam = new WCTeam();
        homeTeam.setId("ARG");
        homeTeam.setName("Argentina");
        homeTeam.setWorldCupGroup("C");
        return homeTeam;
    }

    private WCTeam awayTeam() {
        var awayTeam = new WCTeam();
        awayTeam.setId("KSA");
        awayTeam.setName("Arabia Saudita");
        awayTeam.setWorldCupGroup("C");
        return awayTeam;
    }

    private WCMatch match() {
        var homeTeam = homeTeam();
        var awayTeam = awayTeam();

        var match = new WCMatch();
        match.setId(8L);
        match.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);

        return match;
    }

    private MatchCreateDTO matchCreate() {
        var match = new MatchCreateDTO();
        match.setId(8L);
        match.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        match.setHomeTeamId("ARG");
        match.setAwayTeamId("KSA");
        return match;
    }

    private MatchUpdateResultDTO matchUpdateResult() {
        var match = new MatchUpdateResultDTO();
        match.setId(8L);
        match.setHomeScore(3);
        match.setAwayScore(0);
        return match;
    }

    private PenaltiesRoundDTO createPenaltiesRoundDto() {
        var p1 = new PenaltiesRoundDTO.Penalty();
        p1.setOrder(1);
        p1.setScored(true);

        var p2 = new PenaltiesRoundDTO.Penalty();
        p2.setOrder(2);
        p2.setScored(true);

        var p3 = new PenaltiesRoundDTO.Penalty();
        p3.setOrder(3);
        p3.setScored(true);

        var p4 = new PenaltiesRoundDTO.Penalty();
        p4.setOrder(4);
        p4.setScored(true);

        var p5 = new PenaltiesRoundDTO.Penalty();
        p5.setOrder(5);
        p5.setScored(true);

        var p6 = new PenaltiesRoundDTO.Penalty();
        p6.setOrder(6);
        p6.setScored(true);

        var p7 = new PenaltiesRoundDTO.Penalty();
        p7.setOrder(7);
        p7.setScored(true);

        var p8 = new PenaltiesRoundDTO.Penalty();
        p8.setOrder(8);
        p8.setScored(false);

        var p9 = new PenaltiesRoundDTO.Penalty();
        p9.setOrder(9);
        p9.setScored(true);

        var penaltiesRound = new PenaltiesRoundDTO();
        penaltiesRound.setHomeTeamStarted(true);
        penaltiesRound.setHomeTeamScore(5);
        penaltiesRound.setAwayTeamScore(3);
        penaltiesRound.setHomeTeamPenalties(List.of(p1, p3, p5, p7, p9));
        penaltiesRound.setAwayTeamPenalties(List.of(p2, p4, p6, p8));

        return penaltiesRound;
    }

    private WCPenaltiesRound createPenaltiesRoundEntity() {
        var p1 = new WCPenalty();
        p1.setOrder(1);
        p1.setScored(true);

        var p2 = new WCPenalty();
        p2.setOrder(2);
        p2.setScored(true);

        var p3 = new WCPenalty();
        p3.setOrder(3);
        p3.setScored(true);

        var p4 = new WCPenalty();
        p4.setOrder(4);
        p4.setScored(true);

        var p5 = new WCPenalty();
        p5.setOrder(5);
        p5.setScored(true);

        var p6 = new WCPenalty();
        p6.setOrder(6);
        p6.setScored(true);

        var p7 = new WCPenalty();
        p7.setOrder(7);
        p7.setScored(true);

        var p8 = new WCPenalty();
        p8.setOrder(8);
        p8.setScored(false);

        var p9 = new WCPenalty();
        p9.setOrder(9);
        p9.setScored(true);

        var penaltiesRound = new WCPenaltiesRound();
        penaltiesRound.setHomeTeamStarted(true);
        penaltiesRound.setHomeTeamScore(5);
        penaltiesRound.setAwayTeamScore(3);
        penaltiesRound.setHomeTeamPenalties(List.of(p1, p3, p5, p7, p9));
        penaltiesRound.setAwayTeamPenalties(List.of(p2, p4, p6, p8));

        return penaltiesRound;
    }

    private MatchResponseDTO matchResponse() {
        var response = new MatchResponseDTO();
        response.setId(8L);
        response.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        response.setHomeTeam(MatchResponseDTO.TeamDTO.builder().id("ARG").name("Argentina").build());
        response.setAwayTeam(MatchResponseDTO.TeamDTO.builder().id("KSA").name("Arabia Saudita").build());
        return response;
    }

}