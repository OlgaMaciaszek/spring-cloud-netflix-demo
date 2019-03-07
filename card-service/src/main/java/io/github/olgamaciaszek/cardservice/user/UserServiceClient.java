package io.github.olgamaciaszek.cardservice.user;

import io.github.olgamaciaszek.cardservice.application.CardApplicationDto;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class UserServiceClient {

	private final RestTemplate restTemplate;

	UserServiceClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<User> registerUser(CardApplicationDto.User userDto) {
		return restTemplate.postForEntity("http://user-service/registration",
				userDto,
				User.class);
	}
}