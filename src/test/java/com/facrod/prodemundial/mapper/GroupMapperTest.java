package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.GroupDTO;
import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.entity.WCTeam;
import com.facrod.prodemundial.exceptions.AppException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupMapperTest {

    @Test
    void getGroupListFromTeamsList() {
        var team1 = new WCTeam();
        team1.setId("T01");
        team1.setName("Team 1");
        team1.setWorldCupGroup("A");

        var team2 = new WCTeam();
        team2.setId("T02");
        team2.setName("Team 2");
        team2.setWorldCupGroup("B");

        var team3 = new WCTeam();
        team3.setId("T03");
        team3.setName("Team 3");
        team3.setWorldCupGroup("C");

        var team4 = new WCTeam();
        team4.setId("T04");
        team4.setName("Team 4");
        team4.setWorldCupGroup("D");

        var teams = List.of(team1, team2, team3, team4);

        var teamDto1 = new TeamDTO();
        teamDto1.setId("T01");
        teamDto1.setName("Team 1");
        teamDto1.setGroup("A");
        var groupA = new GroupDTO();
        groupA.setGroup("A");
        groupA.setTeams(List.of(teamDto1));

        var teamDto2 = new TeamDTO();
        teamDto2.setId("T02");
        teamDto2.setName("Team 2");
        teamDto2.setGroup("B");
        var groupB = new GroupDTO();
        groupB.setGroup("B");
        groupB.setTeams(List.of(teamDto2));

        var teamDto3 = new TeamDTO();
        teamDto3.setId("T03");
        teamDto3.setName("Team 3");
        teamDto3.setGroup("C");
        var groupC = new GroupDTO();
        groupC.setGroup("C");
        groupC.setTeams(List.of(teamDto3));

        var teamDto4 = new TeamDTO();
        teamDto4.setId("T04");
        teamDto4.setName("Team 4");
        teamDto4.setGroup("D");
        var groupD = new GroupDTO();
        groupD.setGroup("D");
        groupD.setTeams(List.of(teamDto4));

        var expected = List.of(groupA, groupB, groupC, groupD);

        var actual = GroupMapper.getGroupListFromTeamsList(teams);

        assertNotNull(actual);
        assertNotEquals(0, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void getGroupFromTeamsList_ok() throws AppException {
        var team1 = new WCTeam();
        team1.setId("T01");
        team1.setName("Team 1");
        team1.setWorldCupGroup("C");

        var team2 = new WCTeam();
        team2.setId("T02");
        team2.setName("Team 2");
        team2.setWorldCupGroup("C");

        var team3 = new WCTeam();
        team3.setId("T03");
        team3.setName("Team 3");
        team3.setWorldCupGroup("C");

        var team4 = new WCTeam();
        team4.setId("T04");
        team4.setName("Team 4");
        team4.setWorldCupGroup("C");

        var teams = List.of(team1, team2, team3, team4);

        var teamDto1 = new TeamDTO();
        teamDto1.setId("T01");
        teamDto1.setName("Team 1");
        teamDto1.setGroup("C");

        var teamDto2 = new TeamDTO();
        teamDto2.setId("T02");
        teamDto2.setName("Team 2");
        teamDto2.setGroup("C");

        var teamDto3 = new TeamDTO();
        teamDto3.setId("T03");
        teamDto3.setName("Team 3");
        teamDto3.setGroup("C");

        var teamDto4 = new TeamDTO();
        teamDto4.setId("T04");
        teamDto4.setName("Team 4");
        teamDto4.setGroup("C");

        var expected = new GroupDTO();
        expected.setGroup("C");
        expected.setTeams(List.of(teamDto1, teamDto2, teamDto3, teamDto4));

        var actual = GroupMapper.getGroupFromTeamsList("C", teams);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getGroupFromTeamsList_emptyList() {
        var expected = new AppException(HttpStatus.NOT_FOUND, "El grupo 'C' no existe");

        var actual = assertThrows(AppException.class, () -> GroupMapper.getGroupFromTeamsList("C", List.of()));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

}
