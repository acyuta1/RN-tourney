package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

}
