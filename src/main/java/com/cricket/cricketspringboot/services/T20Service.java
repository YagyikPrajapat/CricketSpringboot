package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.controller.ScoreboardController;
import com.cricket.cricketspringboot.enums.Format;
import com.cricket.cricketspringboot.interfaces.CricketFormat;
import com.cricket.cricketspringboot.model.Scoreboard;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.PlayerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class T20Service implements CricketFormat {
   @Autowired
   private PlayerRepository playerRepository;
   @Autowired
   private PlayerStatsRepository playerStatsRepository;
   @Autowired
   private ScoreboardController scoreboardController;
   @Autowired
   private InningService inningService;
   private Team battingTeam;
   private Team bowlingTeam;
   private static final int OVERS = 20;

   public String matchStarts(Team team1, Team team2){
      battingTeam = team1;
      bowlingTeam = team2;
      Scoreboard scoreboard = new Scoreboard("", Format.T20, 0, 0,
              0, 0, battingTeam.getId(), bowlingTeam.getId(),
              new ArrayList<>());
      scoreboardController.addScoreboard(scoreboard);
      String firstInning = inningService.inningsStart(battingTeam, bowlingTeam, 10000, OVERS, scoreboard.getId());
      scoreboard = scoreboardController.getScoreboard(scoreboard.getId());
      String secondInning = inningService.inningsStart(bowlingTeam, battingTeam, scoreboard.getFirstInningTotalRuns(),
              OVERS, scoreboard.getId());
      scoreboardController.updateScoreboard(scoreboard.getId());
      return "done";
   }

}

