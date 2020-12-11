package io.github.olgamaciaszek.cardservice.config;

import io.github.olgamaciaszek.excluded.DefaultCustomLoadBalancerConfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.ResponseData;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@LoadBalancerClients(defaultConfiguration = DefaultCustomLoadBalancerConfiguration.class)
public class WebClientConfig {

	@Bean
	@LoadBalanced
	@Qualifier("loadBalancedWebClient")
	WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	@Qualifier("webClient")
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	LoadBalancerLifecycle<RequestDataContext, ResponseData, ServiceInstance> testLoadBalancerLifecycle() {
		return new TestLoadBalancerLifecycle();
	}
}



