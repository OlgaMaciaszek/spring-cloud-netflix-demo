package io.github.olgamaciaszek.cardservice.verification;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
		return restTemplate.getForEntity("http://fraud-verifier/cards/verify",
				VerificationResult.class,
				createParameters(verificationApplication));
	}

	private Map<String, Object> createParameters(VerificationApplication verificationApplication) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", verificationApplication.getUserId());
		parameters.put("cardCapacity", verificationApplication.getCardCapacity());
		return parameters;
	}
}
