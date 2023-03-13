package com.cricket.cricketspringboot.controller;

import com.cricket.cricketspringboot.model.PlayerStats;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.PlayerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerStatsController {
   @Autowired
   private PlayerStatsRepository playerStatsRepository;

   @GetMapping("get_player_stats")
   public PlayerStats getPlayerStats(@RequestParam String playerId, @RequestParam String scoreboardId){
      return playerStatsRepository.findByPlayerIdAndScoreboardId(playerId, scoreboardId);
   }
}
