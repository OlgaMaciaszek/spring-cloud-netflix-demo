package io.github.olgamaciaszek.fraudverifier.controllers;

import io.github.olgamaciaszek.fraudverifier.card.CardVerificationDto;
import io.github.olgamaciaszek.fraudverifier.card.CardApplicationVerificationService;
import io.github.olgamaciaszek.fraudverifier.VerificationResult;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping("/verify")
	public ResponseEntity<VerificationResult> verify(@RequestBody CardVerificationDto cardVerificationDTO) {
		VerificationResult result = cardApplicationVerificationService.verify(cardVerificationDTO);
		return ResponseEntity.ok(result);
	}
}
