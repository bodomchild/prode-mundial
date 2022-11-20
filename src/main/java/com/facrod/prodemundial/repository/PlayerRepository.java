package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Player.PlayerId> {

    List<Player> findByTeamId(String teamId);

    @Query("SELECT p.name FROM Player p WHERE p.id = ?1 AND p.teamId = ?2")
    String findNameByIdAndTeamId(Integer id, String teamId);

}
