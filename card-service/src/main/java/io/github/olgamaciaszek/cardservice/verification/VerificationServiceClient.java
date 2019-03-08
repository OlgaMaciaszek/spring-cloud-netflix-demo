package io.github.olgamaciaszek.cardservice.verification;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Olga Maciaszek-Sharma
 */
//@RibbonClient(name = "card-client")  TODO: add rule config?
@Component
public class VerificationServiceClient {

	private final RestTemplate restTemplate;

	VerificationServiceClient(RestTemplate restTemplate) {
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
