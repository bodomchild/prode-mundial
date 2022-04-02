package com.facrod.prodemundial.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "penalty")
public class WCPenalty {

    @Id
    private Long id;

    @Column(name = "penalty_order")
    private int order;

    private boolean scored;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WCPenalty wcPenalty = (WCPenalty) o;
        return id != null && Objects.equals(id, wcPenalty.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
