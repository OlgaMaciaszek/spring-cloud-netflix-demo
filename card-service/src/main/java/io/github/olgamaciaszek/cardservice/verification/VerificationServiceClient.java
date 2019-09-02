package io.github.olgamaciaszek.cardservice.verification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private final RestTemplate restTemplate;

	VerificationServiceClient(@Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<VerificationResult> verify(VerificationApplication verificationApplication) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromHttpUrl("http://fraud-verifier/cards/verify")
						.queryParam("uuid", verificationApplication.getUserId())
				.queryParam("cardCapacity", verificationApplication.getCardCapacity());
		return restTemplate.getForEntity(uriComponentsBuilder.toUriString(),
				VerificationResult.class);
	}
}
