package com.facrod.prodemundial.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "player")
@IdClass(Player.PlayerId.class)
public class Player implements Serializable {

    @Id
    private Integer id;

    @Id
    @Column(name = "team_id")
    private String teamId;

    private String name;

    private String position;

    private int age;

    private int goals;

    private int yellowCards;

    private int redCards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var player = (Player) o;
        return id != null && Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static final class PlayerId implements Serializable {
        private Integer id;
        private String teamId;

        public PlayerId(Integer id, String teamId) {
            this.id = id;
            this.teamId = teamId;
        }
    }

}
