package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
   @Autowired
   private PlayerRepository playerRepository;
   @Autowired
   private TeamRepository teamRepository;

   public String addTeam(Team team){
      team.setId(UUID.randomUUID().toString());
      teamRepository.save(team);
      return "success";
   }

   public Team getTeamById(String id){
      return teamRepository.findById(id).get();
   }

   public Team getTeamByName(String teamName){
      return teamRepository.findByTeamName(teamName);
   }

   public List<String> getPlayerListByTeamName(String teamName){
      Team team = teamRepository.findByTeamName(teamName);
      return team.getPlayers();
   }

   public String deleteByName(String teamName){
      Team team = teamRepository.findByTeamName(teamName);
      teamRepository.delete(team);
      return "done";
   }

   public String updateTeamPlayers(String teamId){
      List<Player> players = playerRepository.findByTeamId(teamId);
      ArrayList<String> playerIdList = new ArrayList<>();
      for(Player player : players){
         playerIdList.add(player.getId());
      }
      Team team = teamRepository.findById(teamId).get();
      team.setPlayers(playerIdList);
      teamRepository.save(team);
      return "success";
   }
}
