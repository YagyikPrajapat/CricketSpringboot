package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.enums.TossChoice;

public class TossService {
   public static boolean toss(TossChoice tossChoice){
      // TODO Add logic to choose random
      int tossResult=(int)(Math.random()*2);
      TossChoice randomTossChoice = (tossResult == 0) ? (TossChoice.HEADS) : (TossChoice.TAILS);
      System.out.println(tossChoice +" "+ randomTossChoice);
      return tossChoice.equals(randomTossChoice);
   }
}
