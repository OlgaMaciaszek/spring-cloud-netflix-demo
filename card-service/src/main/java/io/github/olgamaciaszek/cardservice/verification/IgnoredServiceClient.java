package io.github.olgamaciaszek.cardservice.verification;

import reactor.core.publisher.Mono;

import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class IgnoredServiceClient {

	private final LoadBalancedExchangeFilterFunction lbFunction;

	public IgnoredServiceClient(LoadBalancedExchangeFilterFunction lbFunction) {
		this.lbFunction = lbFunction;
	}

	public Mono<String> callIgnoredService() {
		return WebClient.builder()
				.filter(lbFunction)
				.build().get()
				.uri("http://ignored/test/allowed").
				retrieve().bodyToMono(String.class);
	}
}
