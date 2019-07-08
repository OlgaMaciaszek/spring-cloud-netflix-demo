package io.github.olgamaciaszek.cardservice.verification;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private final WebClient.Builder webClientBuilder;

	VerificationServiceClient(@Qualifier("loadBalancedWebClient") WebClient.Builder webClientBuilder) {
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
