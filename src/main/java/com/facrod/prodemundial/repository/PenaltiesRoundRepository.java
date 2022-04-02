package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.WCPenaltiesRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltiesRoundRepository extends JpaRepository<WCPenaltiesRound, Long> {
}
