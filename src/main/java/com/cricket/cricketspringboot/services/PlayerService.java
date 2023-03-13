package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.enums.PlayerType;
import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {
   @Autowired
   private PlayerRepository playerRepository;

   public String addPlayer(Player player){
      player.setId(UUID.randomUUID().toString());
      playerRepository.save(player);
      return "success";
   }

   public Player getPlayerById(String id){
      return playerRepository.findById(id).get();
   }

   public Player getPlayerByName(String playerName){
      return playerRepository.findPlayerByName(playerName);
   }

   public String deletePlayerByName(String playerName){
      Player player = playerRepository.findPlayerByName(playerName);
      playerRepository.delete(player);
      return "success";
   }

   public String updatePlayer(String playerName, PlayerType playerType){
      Player player = playerRepository.findPlayerByName(playerName);
      player.setPlayerType(playerType);
      playerRepository.save(player);
      return "success";
   }

   public List<Player> getPlayers(String teamId){
      return playerRepository.findByTeamId(teamId);
   }
}
