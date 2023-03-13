package com.cricket.cricketspringboot.repository;

import com.cricket.cricketspringboot.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
    Team findByTeamName(String teamName);
}
