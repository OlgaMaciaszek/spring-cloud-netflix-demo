package io.github.olgamaciaszek.cardservice.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Olga Maciaszek-Sharma
 */
@LoadBalancerClient(name = "verification-service-client", configuration = CustomRibbonConfiguration.class)
@Configuration
public class CustomRibbonConfiguration {

	@Bean
	public IRule ribbonRule() {
		return new RoundRobinRule();
	}
}
