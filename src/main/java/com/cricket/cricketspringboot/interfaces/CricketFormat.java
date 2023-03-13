package com.cricket.cricketspringboot.interfaces;

import com.cricket.cricketspringboot.enums.TossChoice;
import com.cricket.cricketspringboot.model.Team;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public interface CricketFormat {
   String matchStarts(Team team1, Team team2);
}
