package io.github.olgamaciaszek.fraudverifier.controllers;

import java.math.BigDecimal;
import java.util.UUID;

import io.github.olgamaciaszek.fraudverifier.VerificationResult;
import io.github.olgamaciaszek.fraudverifier.card.CardApplicationVerificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/cards")
public class CardApplicationVerificationController {

	private final CardApplicationVerificationService cardApplicationVerificationService;

	public CardApplicationVerificationController(CardApplicationVerificationService cardApplicationVerificationService) {
		this.cardApplicationVerificationService = cardApplicationVerificationService;
	}

	@GetMapping("/verify")
	public ResponseEntity<VerificationResult> verify(@RequestParam UUID uuid,
			@RequestParam BigDecimal cardCapacity) {
		VerificationResult result = cardApplicationVerificationService
				.verify(uuid, cardCapacity);
		return ResponseEntity.ok(result);
	}
}
