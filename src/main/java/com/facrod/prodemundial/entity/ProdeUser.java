package com.facrod.prodemundial.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "prode_user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_prode_user_username", columnNames = "username")
})
public class ProdeUser {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var prodeUser = (ProdeUser) o;
        return id != null && Objects.equals(id, prodeUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
