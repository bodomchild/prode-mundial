package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Player.PlayerId> {
}
