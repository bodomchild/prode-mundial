package com.facrod.prodemundial.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private Long id;

    private int homeTeamScore;

    private int awayTeamScore;

    @OneToMany
    @ToString.Exclude
    private List<WCPenalty> homeTeamPenalties;

    @OneToMany
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
