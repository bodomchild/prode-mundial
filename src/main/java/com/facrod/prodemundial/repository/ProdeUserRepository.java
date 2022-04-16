package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.ProdeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdeUserRepository extends JpaRepository<ProdeUser, String> {

    Optional<ProdeUser> findByUsername(String username);

    boolean existsByUsername(String username);

}
