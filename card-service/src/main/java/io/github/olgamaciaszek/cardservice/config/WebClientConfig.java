package io.github.olgamaciaszek.cardservice.config;

import io.github.olgamaciaszek.cardservice.TestLoadBalancerLifecycle;
import io.github.olgamaciaszek.excluded.CustomLoadBalancerConfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.client.loadbalancer.reactive.RetryableExchangeFilterFunctionLoadBalancerRetryPolicy;
import org.springframework.cloud.client.loadbalancer.reactive.RetryableLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@LoadBalancerClient(name = "fraud-verifier", configuration = CustomLoadBalancerConfiguration.class)
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfiguration.class)
public class WebClientConfig {

//	@Bean
//	RetryableLoadBalancerExchangeFilterFunction retryableLoadBalancerExchangeFilterFunction(
//			LoadBalancerProperties properties,
//			ReactiveLoadBalancer.Factory<ServiceInstance> factory) {
//		return new RetryableLoadBalancerExchangeFilterFunction(
//				new RetryableExchangeFilterFunctionLoadBalancerRetryPolicy(
//						properties),
//				factory, properties);
//	}

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
	LoadBalancerLifecycle<RequestDataContext, ClientResponse, ServiceInstance> testLoadBalancerLifecycle() {
		return new TestLoadBalancerLifecycle();
	}
}



