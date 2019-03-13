package io.github.olgamaciaszek.userservice.registration.verification;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.circuitbreaker.commons.CircuitBreakerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private final RestTemplate restTemplate;
	private final DiscoveryClient discoveryClient;
	private final CircuitBreakerFactory circuitBreakerFactory;

	public VerificationServiceClient(RestTemplate restTemplate,
			DiscoveryClient discoveryClient, CircuitBreakerFactory circuitBreakerFactory) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
		this.circuitBreakerFactory = circuitBreakerFactory;
	}

	public VerificationResult verifyNewUser(UUID userUuid, int userAge) {
		List<ServiceInstance> instances = discoveryClient.getInstances("proxy");
		ServiceInstance instance = instances.stream().findAny()
				.orElseThrow(() -> new IllegalStateException("No proxy instance available"));
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromHttpUrl(instance.getUri()
						.toString() + "/fraud-verifier/users/verify")
				.queryParam("uuid", userUuid)
				.queryParam("age", userAge);
		return circuitBreakerFactory.create("verifyNewUser")
				.run(() -> restTemplate.getForObject(uriComponentsBuilder.toUriString(),
						VerificationResult.class), throwable -> userRejected(userUuid, userAge));
	}

	private VerificationResult userRejected(UUID userUuid, int userAge) {
		return VerificationResult.failed(userUuid);
	}
}
