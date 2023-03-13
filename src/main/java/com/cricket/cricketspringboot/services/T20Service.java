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
   private Team battingTeam;
   private Team bowlingTeam;
   private static final int OVERS = 20;

   public String matchStarts(Team team1, Team team2){
      battingTeam = team1;
      bowlingTeam = team2;
      InningService firstInning = new InningService(playerRepository, playerStatsRepository);
      InningService secondInning = new InningService(playerRepository, playerStatsRepository);
      firstInning.inningsStart(battingTeam, bowlingTeam, 1000, OVERS);
      secondInning.inningsStart(bowlingTeam, battingTeam, firstInning.getTotalRuns() ,OVERS);
      //Saving data in Scoreboard
      Scoreboard scoreboard = new Scoreboard("", Format.T20, firstInning.getTotalRuns(), firstInning.getTotalWickets(),
              secondInning.getTotalRuns(), secondInning.getTotalWickets(), battingTeam.getId(), bowlingTeam.getId(),
              new ArrayList<>());
      scoreboardController.addScoreboard(scoreboard);
      //Saving PlaterStats data in database
      firstInning.savingPlayerStatsOfCurrentMatch(scoreboard.getId());
      secondInning.updatingPlayerStatsOfCurrentMatch(scoreboard.getId());
      scoreboardController.updateScoreboard(scoreboard.getId());
      firstInning.updatingPlayerOverallStats();
      secondInning.updatingPlayerOverallStats();
      return "done";
   }

}

