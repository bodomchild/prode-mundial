package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.entity.WCTeam;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TeamMapperTest {

    @Test
    void toDto() {
        var team = new WCTeam();
        team.setId("ARG");
        team.setName("Argentina");
        team.setWorldCupGroup("C");
        var expected = new TeamDTO();
        expected.setId("ARG");
        expected.setName("Argentina");
        expected.setGroup("C");

        var actual = TeamMapper.toDto(team);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toEntity() {
        var team = new TeamDTO();
        team.setId("ARG");
        team.setName("Argentina");
        team.setGroup("C");
        var expected = new WCTeam();
        expected.setId("ARG");
        expected.setName("Argentina");
        expected.setWorldCupGroup("C");

        var actual = TeamMapper.toEntity(team);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toDtoList() {
        var team = new WCTeam();
        team.setId("ARG");
        team.setName("Argentina");
        team.setWorldCupGroup("C");
        var expectedTeam = new TeamDTO();
        expectedTeam.setId("ARG");
        expectedTeam.setName("Argentina");
        expectedTeam.setGroup("C");
        var expected = Collections.singletonList(expectedTeam);

        var actual = TeamMapper.toDto(Collections.singletonList(team));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}