package com.cricket.cricketspringboot.controller;

import com.cricket.cricketspringboot.enums.Format;
import com.cricket.cricketspringboot.model.Scoreboard;
import com.cricket.cricketspringboot.repository.ScoreboardRepository;
import com.cricket.cricketspringboot.services.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScoreboardController {
   @Autowired
   private ScoreboardService scoreboardService = new ScoreboardService();

   @PostMapping("add_scoreboard")
   public String addScoreboard(Scoreboard scoreboard){
      return scoreboardService.addScoreboard(scoreboard);
   }

   @GetMapping("scoreboard")
   public Scoreboard getScoreboard(String scoreboardId){
      return scoreboardService.getScoreboard(scoreboardId);
   }

   @PutMapping("update_scoreboard")
   public String updateScoreboard(String scoreboardId){
      return scoreboardService.updateScoreboard(scoreboardId);
   }

   @DeleteMapping("delete_scoreboard")
   public void deleteScoreboard(String scoreboardId){
      scoreboardService.deleteScoreboard(scoreboardId);
   }
}
