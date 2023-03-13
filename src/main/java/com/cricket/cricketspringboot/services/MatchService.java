package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.controller.TeamController;
import com.cricket.cricketspringboot.enums.Format;
import com.cricket.cricketspringboot.enums.TossChoice;
import com.cricket.cricketspringboot.interfaces.CricketFormat;
import com.cricket.cricketspringboot.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
   @Autowired
   private CricketFormat cricketFormat;
   @Autowired
   private TeamController teamController;
   @Autowired
   private T20Service t20Service;
   private Team team1;
   private Team team2;
   private Team battingTeam;
   private Team bowlingTeam;
   public String matchFormat(String matchFormat){
      cricketFormat  = matchFormatChoice(matchFormat);
      return cricketFormat.matchStarts(battingTeam, bowlingTeam);
   }

   private CricketFormat matchFormatChoice(String matchFormat){
      Format format = Format.valueOf(matchFormat);
      switch(format){
         case T20:
            return t20Service;
         case ODI:
            System.out.println("ODI");
         case CUSTOM:
            System.out.println("CUSTOM");
         default:
            throw new IllegalArgumentException("Unknown enums.Format");
      }
   }

   public String setMatchDetails(String teamName1, String teamName2){
      team1 = teamController.getTeamByName(teamName1);
      team2 = teamController.getTeamByName(teamName2);
      return "response : true";
   }

   public String tossResult(TossChoice tossChoice){
      boolean tossResult = TossService.toss(tossChoice);
      System.out.println(tossResult);
      //TODO assuming bowling pitch
      bowlingTeam = (tossResult == true) ? (team1) : (team2);
      battingTeam = (tossResult == false) ? (team1) : (team2);
      String result = (tossResult == true) ? (team1.getTeamName()+" won the toss and choose to bowl first") :
              (team2.getTeamName()+" won the toss and choose to bowl first");
      System.out.println(result);
      return "toss";
   }
}
