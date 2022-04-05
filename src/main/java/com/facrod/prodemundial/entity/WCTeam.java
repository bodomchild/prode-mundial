package com.facrod.prodemundial.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "world_cup_team")
public class WCTeam {

    // TODO: 1/4/22 agregar jugadores y tarjetas

    @Id
    private String id;

    private String name;

    private int points;

    private int goals;

    private int goalsAgainst;

    private int goalDifference;

    private int wins;

    private int draws;

    private int losses;

    private int playedGames;

    private String worldCupGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var wcTeam = (WCTeam) o;
        return id != null && Objects.equals(id, wcTeam.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
