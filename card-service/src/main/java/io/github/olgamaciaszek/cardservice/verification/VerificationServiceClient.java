package io.github.olgamaciaszek.cardservice.verification;

import io.github.olgamaciaszek.cardservice.application.config.CustomRibbonConfiguration;
import reactor.core.publisher.Mono;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@LoadBalancerClient(name = "verification-service-client", configuration = CustomRibbonConfiguration.class)
@Component
public class VerificationServiceClient {

	private final WebClient.Builder webClientBuilder;

	VerificationServiceClient(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	public Mono<VerificationResult> verify(VerificationApplication verificationApplication) {
		return webClientBuilder.build().get()
				.uri(uriBuilder -> uriBuilder
						.scheme("http")
						.host("fraud-verifier").path("/cards/verify")
						.queryParam("uuid", verificationApplication.getUserId())
						.queryParam("cardCapacity", verificationApplication
								.getCardCapacity())
						.build())
				.retrieve().bodyToMono(VerificationResult.class);
	}
}
