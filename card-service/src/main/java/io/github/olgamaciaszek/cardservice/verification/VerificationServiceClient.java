package io.github.olgamaciaszek.cardservice.verification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
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
	private final ApplicationContext applicationContext;

	VerificationServiceClient(@Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate, ApplicationContext applicationContext) {
		this.restTemplate = restTemplate;
		this.applicationContext = applicationContext;
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
