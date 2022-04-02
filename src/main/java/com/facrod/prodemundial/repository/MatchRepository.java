package com.facrod.prodemundial.repository;

import com.facrod.prodemundial.entity.WCMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<WCMatch, Long> {
}
