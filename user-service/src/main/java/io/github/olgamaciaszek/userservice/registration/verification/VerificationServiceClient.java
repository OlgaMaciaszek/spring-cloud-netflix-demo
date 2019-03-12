package io.github.olgamaciaszek.userservice.registration.verification;

import java.util.List;
import java.util.UUID;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

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

	public VerificationServiceClient(RestTemplate restTemplate,
			DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	@HystrixCommand(fallbackMethod = "userRejected")
	public VerificationResult verifyNewUser(UUID userUuid, int userAge) {
		List<ServiceInstance> instances = discoveryClient.getInstances("proxy");
		ServiceInstance instance = instances.stream().findAny()
				.orElseThrow(() -> new IllegalStateException("No proxy instance available"));
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromHttpUrl(instance.getUri()
						.toString() + "/fraud-verifier/users/verify")
				.queryParam("uuid", userUuid)
				.queryParam("age", userAge);
		return restTemplate.getForObject(uriComponentsBuilder.toUriString(),
				VerificationResult.class);
	}

	public VerificationResult userRejected(UUID userUuid, int userAge) {
		return VerificationResult.failed(userUuid);
	}
}
