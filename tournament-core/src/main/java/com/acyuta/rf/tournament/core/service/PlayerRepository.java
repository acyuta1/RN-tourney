package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
