package io.github.olgamaciaszek.userservice;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static io.github.olgamaciaszek.userservice.VerificationResult.Status.VERIFICATION_PASSED;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}

	@Bean
	Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(TimeLimiterConfig.custom()
						.timeoutDuration(Duration.ofSeconds(2))
						.build())
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.build());
	}

}

@RestController
@RequestMapping("/registration")
class UserRegistrationController {

	private final UserRegistrationService userRegistrationService;

	UserRegistrationController(UserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}

	@PostMapping
	ResponseEntity<User> registerUser(@RequestBody UserDto userDto,
			UriComponentsBuilder uriComponentsBuilder) {
		User user = userRegistrationService.registerUser(userDto);
		UriComponents uriComponents = uriComponentsBuilder.path("/users/{uuid}")
				.buildAndExpand(user.getUuid());
		return ResponseEntity.created(uriComponents.toUri()).body(user);
	}
}

@Service
class UserRegistrationService {

	private final VerificationServiceClient verificationServiceClient;

	UserRegistrationService(VerificationServiceClient verificationServiceClient) {
		this.verificationServiceClient = verificationServiceClient;
	}

	User registerUser(UserDto userDto) {
		User user = new User(userDto);
		verifyUser(user);
		return user;
	}

	private void verifyUser(User user) {
		VerificationResult verificationResult = verificationServiceClient
				.verifyNewUser(user.getUuid(), user.getAge());
		user.setStatus(VERIFICATION_PASSED.equals(verificationResult.getStatus()) ?
				User.Status.OK : User.Status.FRAUD);
	}
}

@Component
class VerificationServiceClient {

	private final RestTemplate restTemplate;
	private final DiscoveryClient discoveryClient;
	private final CircuitBreakerFactory circuitBreakerFactory;
	private final Timer verifyNewUserTimer;

	public VerificationServiceClient(RestTemplateBuilder restTemplateBuilder,
			DiscoveryClient discoveryClient, CircuitBreakerFactory circuitBreakerFactory, MeterRegistry meterRegistry) {
		this.restTemplate = restTemplateBuilder.build();
		this.discoveryClient = discoveryClient;
		this.circuitBreakerFactory = circuitBreakerFactory;
		this.verifyNewUserTimer = meterRegistry.timer("verifyNewUser");
	}

	public VerificationResult verifyNewUser(UUID userUuid, int userAge) {
		return verifyNewUserTimer.record(() -> {
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
		});
	}

	private VerificationResult userRejected(UUID userUuid, int userAge) {
		return VerificationResult.failed(userUuid);
	}
}