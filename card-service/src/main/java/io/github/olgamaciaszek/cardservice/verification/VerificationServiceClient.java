package io.github.olgamaciaszek.cardservice.verification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.COOKIE, "test=testCookie");
		HttpEntity request = new HttpEntity(headers);
		return restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET,
				request, VerificationResult.class);
//		return restTemplate.getForEntity(uriComponentsBuilder.toUriString(),
//				VerificationResult.class);
	}
}
