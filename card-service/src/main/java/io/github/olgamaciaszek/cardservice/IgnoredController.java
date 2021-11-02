package io.github.olgamaciaszek.cardservice;

import io.github.olgamaciaszek.cardservice.verification.IgnoredServiceClient;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
public class IgnoredController {

	private final IgnoredServiceClient client;

	public IgnoredController(IgnoredServiceClient client) {
		this.client = client;
	}

	@GetMapping("/ignored")
	Mono<String> getIngored() {
		return client.callIgnoredService();
	}
}
