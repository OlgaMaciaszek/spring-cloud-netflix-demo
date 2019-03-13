package io.github.olgamaciaszek.userservice;

import java.util.List;
import java.util.UUID;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
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
@EnableHystrixDashboard
@EnableHystrix
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
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
		user.setStatus(VERIFICATION_PASSED.equals(verificationResult.getStatus()) ? User.Status.OK : User.Status.FRAUD);
	}
}

@Component
class VerificationServiceClient {

	private final RestTemplate restTemplate;
	private final DiscoveryClient discoveryClient;

	VerificationServiceClient(RestTemplate restTemplate,
			DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	@HystrixCommand(fallbackMethod = "userRejected")
	public VerificationResult verifyNewUser(UUID userUuid, int userAge) {
		List<ServiceInstance> instances = discoveryClient.getInstances("proxy");
		ServiceInstance instance = instances.stream().findAny()
				.orElseThrow(() -> new IllegalStateException("No zuul-proxy instance available"));
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
