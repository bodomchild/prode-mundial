package com.facrod.prodemundial.entity;

import com.facrod.prodemundial.enums.MatchResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "world_cup_match")
public class WCMatch {

    @Id
    private Long id;

    private LocalDateTime startTime;

    @ManyToOne
    private WCTeam homeTeam;

    @ManyToOne
    private WCTeam awayTeam;

    private int homeScore;

    private int awayScore;

    @Enumerated(EnumType.STRING)
    private MatchResult result;

    private boolean extraTime;

    private int extraTimeHomeScore;

    private int extraTimeAwayScore;

    private boolean penalties;

    @OneToOne
    private WCPenaltiesRound penaltiesRound;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WCMatch wcMatch = (WCMatch) o;
        return id != null && Objects.equals(id, wcMatch.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
