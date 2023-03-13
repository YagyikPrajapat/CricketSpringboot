package com.cricket.cricketspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerStats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats {
   @Id
   private String id;
   private String playerId;
   private String scoreboardId;
   private String playerName;
   private int runsScored;
   private int ballsPlayed;
   private int runsGiven;
   private int ballsThrown;
   private int wicketsTaken;

}
