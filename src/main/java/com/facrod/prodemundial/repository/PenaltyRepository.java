package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.WCPenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRepository extends JpaRepository<WCPenalty, Long> {
}
