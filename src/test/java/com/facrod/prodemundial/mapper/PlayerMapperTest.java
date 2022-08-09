package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerMapperTest {

    @Test
    void toEntity() {
        var dto = new PlayerCreateDTO();
        dto.setId(10);
        dto.setTeamId("ARG");
        dto.setName("Lionel Messi");
        dto.setPosition("FW");
        dto.setAge(35);

        var expected = new Player();
        expected.setId(10);
        expected.setTeamId("ARG");
        expected.setName("Lionel Messi");
        expected.setPosition("FW");
        expected.setAge(35);

        var actual = PlayerMapper.toEntity(dto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toDto() {
        var entity = new Player();
        entity.setId(10);
        entity.setTeamId("ARG");
        entity.setName("Lionel Messi");
        entity.setPosition("FW");
        entity.setAge(35);
        entity.setGoals(10);
        entity.setYellowCards(2);
        entity.setRedCards(1);

        var expected = new PlayerResponseDTO();
        expected.setId(10);
        expected.setTeamId("ARG");
        expected.setName("Lionel Messi");
        expected.setPosition("FW");
        expected.setAge(35);
        expected.setGoals(10);
        expected.setYellowCards(2);
        expected.setRedCards(1);

        var actual = PlayerMapper.toDto(entity);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toDtoList() {
        var entity = new Player();
        entity.setId(10);
        entity.setTeamId("ARG");
        entity.setName("Lionel Messi");
        entity.setPosition("FW");
        entity.setAge(35);
        entity.setGoals(10);
        entity.setYellowCards(2);
        entity.setRedCards(1);
        var dto = new PlayerResponseDTO();
        dto.setId(10);
        dto.setTeamId("ARG");
        dto.setName("Lionel Messi");
        dto.setPosition("FW");
        dto.setAge(35);
        dto.setGoals(10);
        dto.setYellowCards(2);
        dto.setRedCards(1);

        var expected = Collections.singletonList(dto);

        var actual = PlayerMapper.toDto(Collections.singletonList(entity));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}