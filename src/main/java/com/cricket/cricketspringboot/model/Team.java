package com.cricket.cricketspringboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "Team")
@Getter
@Setter
public class Team {

   @Id
   private String id;

   private String teamName;
   private ArrayList<String> players = new ArrayList<>();

   public Team() {
   }
}
