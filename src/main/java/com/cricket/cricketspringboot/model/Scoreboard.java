package com.cricket.cricketspringboot.model;

import com.cricket.cricketspringboot.enums.Format;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Document(collection = "Scoreboard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scoreboard {
   @Id
   private String id;
   private Format format;
   private int firstInningTotalRuns;
   private int  firstInningWicketsLoss;
   private int secondInningTotalRuns;
   private int  secondInningWicketsLoss;
   private String firstInningTeamId;
   private String secondInningTeamId;
   private List<String> playerStats;
}
