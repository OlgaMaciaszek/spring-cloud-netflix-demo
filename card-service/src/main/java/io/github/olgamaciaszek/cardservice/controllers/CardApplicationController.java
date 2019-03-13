package io.github.olgamaciaszek.cardservice.controllers;

import io.github.olgamaciaszek.cardservice.application.ApplicationResult;
import io.github.olgamaciaszek.cardservice.application.CardApplicationDto;
import io.github.olgamaciaszek.cardservice.application.CardApplicationService;
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
public class CardApplicationController {

	private final CardApplicationService cardApplicationService;

	public CardApplicationController(CardApplicationService cardApplicationService) {
		this.cardApplicationService = cardApplicationService;
	}

	@PostMapping
	public Mono<ApplicationResult> apply(@RequestBody CardApplicationDto applicationDTO) {
		return cardApplicationService.registerApplication(applicationDTO);
	}
}
