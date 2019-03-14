package io.github.olgamaciaszek.cardservice;

import io.github.olgamaciaszek.cardservice.excluded.CustomRibbonConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = CustomRibbonConfiguration.class)
public class CardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardServiceApplication.class, args);
	}

}

