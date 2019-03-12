package io.github.olgamaciaszek.cardservice.user;

import io.github.olgamaciaszek.cardservice.application.CardApplicationDto;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class UserServiceClient {

	private final WebClient.Builder webClientBuilder;

	UserServiceClient(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	public Mono<User> registerUser(CardApplicationDto.User userDto) {
		return webClientBuilder.build()
				.post().uri("http://user-service/registration")
				.syncBody(userDto)
				.retrieve()
				.bodyToMono(User.class);
	}
}