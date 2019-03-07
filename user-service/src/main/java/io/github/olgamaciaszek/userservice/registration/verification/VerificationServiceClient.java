package io.github.olgamaciaszek.userservice.registration.verification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.github.olgamaciaszek.userservice.model.User;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private final RestTemplate restTemplate;
	private final DiscoveryClient discoveryClient;

	public VerificationServiceClient(RestTemplate restTemplate,
			DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	public VerificationResult verifyNewUser(UUID userUuid, int userAge) {
		List<ServiceInstance> instances = discoveryClient.getInstances("proxy");
		ServiceInstance instance = instances.stream().findAny()
				.orElseThrow(() -> new IllegalStateException("No zuul-proxy instance available"));
		return restTemplate.getForObject(instance.getUri().toString() + "/fraud-verifier",
				VerificationResult.class,
				createParameters(userUuid, userAge));
	}

	private Map<String, Object> createParameters(UUID userUuid, int userAge) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("uuid",userUuid);
		parameters.put("age", userAge);
		return parameters;
	}
}
