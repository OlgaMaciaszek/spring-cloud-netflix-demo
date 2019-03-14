package io.github.olgamaciaszek.cardservice.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@RibbonClient(name = "verification-service-client", configuration = CustomRibbonConfiguration.class)
public class CustomRibbonConfiguration {

	@Bean
	public IRule ribbonRule() {
		return new RoundRobinRule();
	}
}
