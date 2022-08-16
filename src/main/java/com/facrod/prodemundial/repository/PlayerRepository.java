package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Player.PlayerId> {

    List<Player> findByTeamId(String teamId);

}
