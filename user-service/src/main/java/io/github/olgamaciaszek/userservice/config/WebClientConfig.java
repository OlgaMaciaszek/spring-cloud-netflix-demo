package io.github.olgamaciaszek.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
public class WebClientConfig {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
