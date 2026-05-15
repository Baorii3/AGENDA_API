package com.agenda.itic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IticApplication {

	public static void main(String[] args) {
		SpringApplication.run(IticApplication.class, args);
	}

}
