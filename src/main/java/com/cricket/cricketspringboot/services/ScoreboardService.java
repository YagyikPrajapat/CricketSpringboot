package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.model.PlayerStats;
import com.cricket.cricketspringboot.model.Scoreboard;
import com.cricket.cricketspringboot.repository.PlayerStatsRepository;
import com.cricket.cricketspringboot.repository.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ScoreboardService {
   @Autowired
   private ScoreboardRepository scoreboardRepository;
   @Autowired
   private PlayerStatsRepository playerStatsRepository;

   public String addScoreboard(Scoreboard scoreboard){
      scoreboard.setId(UUID.randomUUID().toString());
      scoreboardRepository.save(scoreboard);
      return "response : success";
   }

   public Scoreboard getScoreboard(String id){
      return scoreboardRepository.findById(id).get();
   }

   public String updateScoreboard(String scoreboardId){
      ArrayList<PlayerStats> playerStats = playerStatsRepository.findByScoreboardId(scoreboardId);
      ArrayList<String> playerStatsList = new ArrayList<>();
      for(PlayerStats playerStatsId : playerStats){
         playerStatsList.add(playerStatsId.getId());
      }
      Scoreboard scoreboard = scoreboardRepository.findById(scoreboardId).get();
      scoreboard.setPlayerStats(playerStatsList);
      scoreboardRepository.save(scoreboard);
      return "success";
   }

   public void deleteScoreboard(String id){
       scoreboardRepository.deleteById(id);
   }
}
