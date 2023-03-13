package com.cricket.cricketspringboot;

import com.cricket.cricketspringboot.services.InningService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CricketspringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(CricketspringbootApplication.class, args);
	}
}
