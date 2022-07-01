package io.github.olgamaciaszek.ignoredservice;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/test")
class IgnoredServiceController {

	@GetMapping("/test/{id}")
	Mono<String> test(@PathVariable Map<Mono<String>, Mono<String>> map) {
		return Mono.just("Ignored service called");
	}

	@GetMapping("/allowed")
	String allowed() {
		return "Allowed endpoint called";
	}

}
