package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.enums.PlayerType;
import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.model.PlayerStats;
import com.cricket.cricketspringboot.model.Scoreboard;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.PlayerStatsRepository;
import com.cricket.cricketspringboot.repository.ScoreboardRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
public class InningService {
   private Team battingTeam;
   private Team bowlingTeam;
   private List<String> batterList;
   private List<String> bowlerList;
   @Autowired
   private PlayerRepository playerRepository;
   @Autowired
   private PlayerStatsRepository playerStatsRepository;
   @Autowired
   private ScoreboardRepository scoreboardRepository;

   private HashMap<String, Integer> playerRuns = new HashMap<>();
   private HashMap<String, Integer> ballsThrown = new HashMap<>();
   private HashMap<String, Integer> ballsPlayed = new HashMap<>();
   private HashMap<String, Integer> runsGiven = new HashMap<>();
   private HashMap<String, Integer> wicketsTaken = new HashMap<>();

   private int strike , non_strike , next_batsman, bowler ;
   private int totalWickets, totalRuns ;
   public String inningsStart(Team battingTeam1, Team bowlingTeam1, int target, int overs, String scoreboardId){
      initialisation(battingTeam1, bowlingTeam1);
      System.out.println(target + " " + totalRuns);
      for (int i = 0; i < overs; i++) {
         int ball = 0, flag = 0;
         for (int j = 0; j < 6; j++) {
            if (totalWickets >= 10 || totalRuns > target) {
               flag = 1;
               break;
            }
            ball = ballDecision();
            updateBallsPlayedAndThrown();  //updating model.Scoreboard
            ballResult(ball);
         }
         if (flag == 1) break;
         swapPlayers();
         bowler++;
         if (bowler > 10) bowler = 5;
       //  System.out.println(" ** over ends **");
      }
      boolean isFirstInning = false;
      if(target == 10000) isFirstInning = true;
      savingData(scoreboardId, isFirstInning);
      return "";
   }
   private void savingData(String scoreboardId, boolean isFirstInning){
      updatingScoreboard(scoreboardId, isFirstInning);
      if(isFirstInning) savingPlayerStatsOfCurrentMatch(scoreboardId);
      else updatingPlayerStatsOfCurrentMatch(scoreboardId);
      updatingPlayerOverallStats();
   }

   private int ballDecision(){
      int ball = 0;
      Player player = playerRepository.findById(batterList.get(strike)).get();
      if(player.getPlayerType() == PlayerType.BATTER){
         ball = (int) (Math.random()*7) + (int) (Math.random()*2); //if batter then less chance of losing wicket
      }
      else ball = (int)(Math.random()*8);
      return ball;
   }

   private void ballResult(int ball) {
      if (ball == 7) {
         //System.out.print("Wicket ");
         totalWickets++;
         updateWicketsTaken();
         strike = next_batsman;
         next_batsman++;
      } else {
        // System.out.print(ball + " ");
         totalRuns += ball;
         updateRunsScoredAndRunsGiven(ball);
         if (ball % 2 != 0) {
            swapPlayers();
         }
      }
   }

   private void swapPlayers() {
      int temp = strike;
      strike = non_strike;
      non_strike = temp;
   }

   private void updateWicketsTaken(){
      if(!wicketsTaken.containsKey(bowlerList.get(bowler))){
         wicketsTaken.put(bowlerList.get(bowler), 1);
      }
      else wicketsTaken.put(bowlerList.get(bowler), wicketsTaken.get(bowlerList.get(bowler))+1);
   }

   private void updateRunsScoredAndRunsGiven(int ball) {
      if(!playerRuns.containsKey(batterList.get(strike))){
         playerRuns.put(batterList.get(strike), ball);
      }
      else playerRuns.put(batterList.get(strike), playerRuns.get(batterList.get(strike))+ball);

      if(!runsGiven.containsKey(bowlerList.get(bowler))){
         runsGiven.put(bowlerList.get(bowler), ball);
      }
      else runsGiven.put(bowlerList.get(bowler), runsGiven.get(bowlerList.get(bowler))+ball);
   }

   private void updateBallsPlayedAndThrown() {
      if(!ballsThrown.containsKey(bowlerList.get(bowler))){
         ballsThrown.put(bowlerList.get(bowler), 1);
      }
      else ballsThrown.put(bowlerList.get(bowler), ballsThrown.get(bowlerList.get(bowler))+1);

      if(!ballsPlayed.containsKey(batterList.get(strike))){
         ballsPlayed.put(batterList.get(strike), 1);
      }
      else ballsPlayed.put(batterList.get(strike), ballsPlayed.get(batterList.get(strike))+1);
   }

   private void savingPlayerStatsOfCurrentMatch(String scoreboardId){
      for(int i=0;i<11;i++){
         Player player = playerRepository.findById(batterList.get(i)).get();
         if(!playerRuns.containsKey(player.getId())) playerRuns.put(player.getId(), 0);
         if(!ballsPlayed.containsKey(player.getId())) ballsPlayed.put(player.getId(), 0);
         PlayerStats battingPlayerStats = new PlayerStats(UUID.randomUUID().toString(), player.getId(), scoreboardId,
                 player.getName(), playerRuns.get(player.getId()), ballsPlayed.get(player.getId()),0,0,0);
         playerStatsRepository.save(battingPlayerStats);
      }
      for(int i=0;i<11;i++){
         Player player = playerRepository.findById(bowlerList.get(i)).get();
         if(!runsGiven.containsKey(player.getId())) runsGiven.put(player.getId(), 0);
         if(!ballsThrown.containsKey(player.getId())) ballsThrown.put(player.getId(), 0);
         if(!wicketsTaken.containsKey(player.getId())) wicketsTaken.put(player.getId(), 0);
         PlayerStats bowlingPlayerStats = new PlayerStats(UUID.randomUUID().toString(), player.getId(), scoreboardId,
                 player.getName() ,0, 0,runsGiven.get(player.getId()),
                 ballsThrown.get(player.getId()),wicketsTaken.get(player.getId()));
         playerStatsRepository.save(bowlingPlayerStats);
      }
   }
   private void updatingPlayerStatsOfCurrentMatch(String scoreboardId){
      for(int i=0;i<11;i++){
         PlayerStats battingPlayerStats = playerStatsRepository.findByPlayerIdAndScoreboardId(batterList.get(i), scoreboardId);
         PlayerStats bowlingPlayerStats = playerStatsRepository.findByPlayerIdAndScoreboardId(bowlerList.get(i), scoreboardId);
         initialStats(battingPlayerStats, bowlingPlayerStats);
         battingPlayerStats.setRunsScored(playerRuns.get(battingPlayerStats.getPlayerId()));
         battingPlayerStats.setBallsPlayed(ballsPlayed.get(battingPlayerStats.getPlayerId()));
         bowlingPlayerStats.setRunsGiven(runsGiven.get(bowlingPlayerStats.getPlayerId()));
         bowlingPlayerStats.setBallsThrown(ballsThrown.get(bowlingPlayerStats.getPlayerId()));
         bowlingPlayerStats.setWicketsTaken(wicketsTaken.get(bowlingPlayerStats.getPlayerId()));
         playerStatsRepository.save(battingPlayerStats);
         playerStatsRepository.save(bowlingPlayerStats);
      }
   }

   private void updatingScoreboard(String scoreboardId, boolean isFirstInning){
      Scoreboard scoreboard = scoreboardRepository.findById(scoreboardId).get();
      if(isFirstInning == true){
         scoreboard.setFirstInningTotalRuns(totalRuns);
         scoreboard.setFirstInningWicketsLoss(totalWickets);
         scoreboardRepository.save(scoreboard);
         return;
      }
      scoreboard.setSecondInningTotalRuns(totalRuns);
      scoreboard.setSecondInningWicketsLoss(totalWickets);
      scoreboardRepository.save(scoreboard);
   }

   private void updatingPlayerOverallStats(){
      for(int i=0;i<11;i++){
         Player battingPlayer = playerRepository.findById(batterList.get(i)).get();
         battingPlayer.setTotalRuns(battingPlayer.getTotalRuns() + playerRuns.get(battingPlayer.getId()));
         playerRepository.save(battingPlayer);
      }
      for(int i=0;i<11;i++){
         Player bowlingPlayer = playerRepository.findById(bowlerList.get(i)).get();
         bowlingPlayer.setTotalWickets(bowlingPlayer.getTotalWickets()+ wicketsTaken.get(bowlingPlayer.getId()));
         playerRepository.save(bowlingPlayer);
      }
   }

   private void initialStats(PlayerStats battingPlayerStats, PlayerStats bowlingPlayerStats){
      if(!playerRuns.containsKey(battingPlayerStats.getPlayerId())) playerRuns.put(battingPlayerStats.getPlayerId(), 0);
      if(!ballsPlayed.containsKey(battingPlayerStats.getPlayerId())) ballsPlayed.put(battingPlayerStats.getPlayerId(), 0);
      if(!runsGiven.containsKey(bowlingPlayerStats.getPlayerId())) runsGiven.put(bowlingPlayerStats.getPlayerId(), 0);
      if(!ballsThrown.containsKey(bowlingPlayerStats.getPlayerId())) ballsThrown.put(bowlingPlayerStats.getPlayerId(), 0);
      if(!wicketsTaken.containsKey(bowlingPlayerStats.getPlayerId())) wicketsTaken.put(bowlingPlayerStats.getPlayerId(), 0);
   }

   private void initialisation(Team battingTeam, Team bowlingTeam){
      this.battingTeam = battingTeam;
      this.bowlingTeam = bowlingTeam;
      batterList = battingTeam.getPlayers();
      bowlerList = bowlingTeam.getPlayers();
      strike=0;
      non_strike = 1; next_batsman = 2; bowler = 5;
      totalRuns = 0; totalWickets = 0;
   }

}
