package com.facrod.prodemundial.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerSortTest {

    @Test
    void value() {
        var goalsSort = PlayerSort.GOALS;
        var redCardsSort = PlayerSort.RED_CARDS;
        var yellowCardsSort = PlayerSort.YELLOW_CARDS;

        var expectedGoalsValue = "goals";
        var expectedRedCardsValue = "redCards";
        var expectedYellowCardsValue = "yellowCards";

        var goalsValue = goalsSort.value();
        var redCardsValue = redCardsSort.value();
        var yellowCardsValue = yellowCardsSort.value();

        assertNotNull(goalsValue);
        assertNotNull(redCardsValue);
        assertNotNull(yellowCardsValue);
        assertEquals(expectedGoalsValue, goalsValue);
        assertEquals(expectedRedCardsValue, redCardsValue);
        assertEquals(expectedYellowCardsValue, yellowCardsValue);
    }

    @Test
    void fromString_goals() {
        var sortBy = "goals";

        var result = PlayerSort.fromString(sortBy);

        assertNotNull(result);
        assertEquals(PlayerSort.GOALS, result);
    }

    @Test
    void fromString_redCards() {
        var sortBy = "redCards";

        var result = PlayerSort.fromString(sortBy);

        assertNotNull(result);
        assertEquals(PlayerSort.RED_CARDS, result);
    }

    @Test
    void fromString_yellowCards() {
        var sortBy = "yellowCards";

        var result = PlayerSort.fromString(sortBy);

        assertNotNull(result);
        assertEquals(PlayerSort.YELLOW_CARDS, result);
    }

    @Test
    void fromString_default() {
        var sortBy = "";

        var result = PlayerSort.fromString(sortBy);

        assertNotNull(result);
        assertEquals(PlayerSort.GOALS, result);
    }

}