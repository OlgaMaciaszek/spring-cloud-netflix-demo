package io.github.olgamaciaszek.cardservice.config;

import io.github.olgamaciaszek.excluded.DefaultCustomLoadBalancerConfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@LoadBalancerClients(defaultConfiguration = DefaultCustomLoadBalancerConfiguration.class)
public class RestTemplateConfig {

	@Bean
	@LoadBalanced
	@Qualifier("loadBalancedRestTemplate")
	RestTemplate loadBalancedRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@Qualifier("webClient")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}



