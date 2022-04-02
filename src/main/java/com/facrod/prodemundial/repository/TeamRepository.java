package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.WCTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<WCTeam, String> {

    @Query("SELECT t.name, t.playedGames, t.wins, t.draws, t.losses, t.points FROM WCTeam t")
    List<WCTeam> findAllTeams();

}
