package io.github.olgamaciaszek.userservice.config;

import java.time.Duration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.circuitbreaker.commons.Customizer;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
public class WebClientConfig {

	@Bean
	RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(TimeLimiterConfig.custom()
						.timeoutDuration(Duration.ofSeconds(2))
						.build())
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.build());
	}

}
