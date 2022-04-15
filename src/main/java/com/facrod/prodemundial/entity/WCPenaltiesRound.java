package com.facrod.prodemundial.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "penalties_round")
public class WCPenaltiesRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean homeTeamStarted;

    private int homeTeamScore;

    private int awayTeamScore;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<WCPenalty> homeTeamPenalties;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<WCPenalty> awayTeamPenalties;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var that = (WCPenaltiesRound) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
