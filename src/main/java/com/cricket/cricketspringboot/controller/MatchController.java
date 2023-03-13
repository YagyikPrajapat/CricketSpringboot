package com.cricket.cricketspringboot.controller;


import com.cricket.cricketspringboot.enums.TossChoice;
import com.cricket.cricketspringboot.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
   @Autowired
   private MatchService matchService;

   @PostMapping("/set_team_names")
   public String setMatchDetails(@RequestParam String teamName1, @RequestParam String teamName2){
      matchService.setMatchDetails(teamName1, teamName2);
      return "success";
   }

   @PostMapping("/toss")
   public String toss(@RequestParam TossChoice tossChoice){
      matchService.tossResult(tossChoice);
      return "success";
   }
   @GetMapping("/match")
   public String matchFormat(@RequestParam String format){
      matchService.matchFormat(format);
      return "match";
   }


}
