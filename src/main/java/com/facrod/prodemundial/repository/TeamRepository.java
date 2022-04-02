package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.WCTeam;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<WCTeam, String> {

    List<WCTeam> findByWorldCupGroup(String worldCupGroup, Sort sort);

}
