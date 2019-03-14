package io.github.olgamaciaszek.cardservice.config;

import io.github.olgamaciaszek.excluded.CustomRibbonConfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@RibbonClient(name = "fraud-verifier", configuration = CustomRibbonConfiguration.class)
public class WebClientConfig {

	@Bean
	@LoadBalanced
	@Qualifier("loadBalancedRestTemplate")
	RestTemplate loadBalancedRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@Qualifier("restTemplate")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}



