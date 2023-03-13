package com.cricket.cricketspringboot.controller;

import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.services.TeamService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {
   @Autowired
   private TeamService teamService;

   public TeamController(){}

   @PostMapping("add_team")
   public String addTeam(@RequestBody Team team){
      team.setPlayers(new ArrayList<>());
      teamService.addTeam(team);
      return "success";
   }

   @GetMapping("get_team")
   public Team getTeamById(String id){
      return teamService.getTeamById(id);
   }

   @GetMapping("get_by_name")
   public Team getTeamByName(@RequestParam String teamName){
      return teamService.getTeamByName(teamName);
   }

   @GetMapping("players")
   public List<String> getPlayerListByTeamName(@RequestParam String teamName){
      return teamService.getPlayerListByTeamName(teamName);
   }

   @DeleteMapping("delete_team")
   public String deleteTeamByName(@RequestParam String teamName){
      return teamService.deleteByName(teamName);
   }

   @PutMapping("update_team")
   public String updateTeamPlayers(@RequestParam String teamId){
      return teamService.updateTeamPlayers(teamId);
   }
}
