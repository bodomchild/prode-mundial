package com.facrod.prodemundial.enums;

import java.util.Arrays;

public enum PlayerSort {

    GOALS("goals"),
    RED_CARDS("redCards"),
    YELLOW_CARDS("yellowCards");

    private final String value;

    PlayerSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static PlayerSort fromString(String sortBy) {
        return Arrays.stream(values())
                .filter(s -> s.value.equals(sortBy))
                .findFirst()
                .orElse(GOALS);
    }

}
