package io.github.olgamaciaszek.cardservice.user;

import java.util.List;

import io.github.olgamaciaszek.cardservice.application.CardApplicationDto;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class UserServiceClient {

	private final WebClient.Builder webClientBuilder;
	private final DiscoveryClient discoveryClient;

	UserServiceClient(@Qualifier("webClient") WebClient.Builder webClientBuilder,
			DiscoveryClient discoveryClient) {
		this.webClientBuilder = webClientBuilder;
		this.discoveryClient = discoveryClient;
	}

	public Mono<User> registerUser(CardApplicationDto.User userDto) {
		List<ServiceInstance> instances = discoveryClient.getInstances("proxy");
		ServiceInstance instance = instances.stream().findAny()
				.orElseThrow(() -> new IllegalStateException("No proxy instance available"));
		return webClientBuilder.build()
				.post().uri(instance.getUri()
						.toString() + "/user-service/registration")
				.bodyValue(userDto)
				.retrieve()
				.bodyToMono(User.class);
	}
}