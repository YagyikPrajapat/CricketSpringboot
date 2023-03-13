package com.cricket.cricketspringboot.repository;

import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.model.PlayerStats;
import com.cricket.cricketspringboot.model.Scoreboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PlayerStatsRepository extends MongoRepository<PlayerStats, String> {
   public ArrayList<PlayerStats> findByScoreboardId(String scoreboardId);
   public PlayerStats findByPlayerIdAndScoreboardId(String playerId, String scoreboardId);
}
