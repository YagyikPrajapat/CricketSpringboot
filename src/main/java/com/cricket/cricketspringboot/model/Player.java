package com.cricket.cricketspringboot.model;

import com.cricket.cricketspringboot.enums.PlayerType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Player")
@Getter
@Setter
public class Player {
   @Id
   private String id;
   private String name;
   private String teamId;
   private PlayerType playerType;
   private int totalRuns;
   private int totalWickets;

   public Player(){}
}
