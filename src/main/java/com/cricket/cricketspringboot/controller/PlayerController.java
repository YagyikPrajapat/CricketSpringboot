package com.cricket.cricketspringboot.controller;

import com.cricket.cricketspringboot.enums.PlayerType;
import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.TeamRepository;
import com.cricket.cricketspringboot.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class PlayerController {
   @Autowired
   PlayerRepository playerRepository;
   @Autowired
   private PlayerService playerService;
   @Autowired
   private TeamRepository teamRepository;

   @PostMapping("add_player")
   public String addPlayer(@RequestBody Player player){
      RedirectView redirectView = new RedirectView("add_player");
      playerService.addPlayer(player);
      return "success";
   }

   @GetMapping("get_player")
   public Player getPlayerById(String id){
      return playerService.getPlayerById(id);
   }

   @GetMapping("get_player_by_name")
   public Player getPlayerByName(@RequestParam String playerName){
      return playerService.getPlayerByName(playerName);
   }

   @DeleteMapping("delete_player")
   public String deletePlayerByName(@RequestParam String playerName){
      return playerService.deletePlayerByName(playerName);
   }

   @PutMapping("update_player")
   public String updatePlayer(@RequestParam String name, @RequestParam PlayerType playerType){
      return playerService.updatePlayer(name, playerType);
   }

   @GetMapping("get_players")
   public List<Player> getPlayers(String teamId){
      return playerService.getPlayers(teamId);
   }

   @PutMapping("update")
   public String updatePlayer1(@RequestParam String teamId){
      List<Player> players = playerRepository.findByTeamId(teamId);
      for(Player player : players){
         player.setTotalRuns(0);
         player.setTotalWickets(0);
         playerRepository.save(player);
      }
      return "playerService.updatePlayer(name, playerType);";
   }

//   @GetMapping("add_player")
//   public ModelAndView getAddPlayer(){
//      ModelAndView mav = new ModelAndView("player");
//      Player player = new Player();
//      mav.addObject("player", player);
//      mav.addObject("team", new Team());
//      return mav;
//   }
}
