package com.facrod.prodemundial.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "world_cup_team", uniqueConstraints = {
        @UniqueConstraint(name = "uk_team_name", columnNames = "name")
})
public class WCTeam implements Serializable {

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

    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "id")
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<Player> players;

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
