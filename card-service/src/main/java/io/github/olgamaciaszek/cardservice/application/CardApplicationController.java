package io.github.olgamaciaszek.cardservice.application;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/application")
class CardApplicationController {

	private final CardApplicationService cardApplicationService;

	CardApplicationController(CardApplicationService cardApplicationService) {
		this.cardApplicationService = cardApplicationService;
	}

	@PostMapping
	Mono<ApplicationResult> apply(@RequestBody CardApplicationDto applicationDTO) {
		return cardApplicationService.registerApplication(applicationDTO);
	}
}
