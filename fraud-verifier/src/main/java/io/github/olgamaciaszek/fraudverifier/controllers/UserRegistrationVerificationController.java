package io.github.olgamaciaszek.fraudverifier.controllers;

import java.util.UUID;

import io.github.olgamaciaszek.fraudverifier.VerificationResult;
import io.github.olgamaciaszek.fraudverifier.user.UserRegistrationVerificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/users")
public class UserRegistrationVerificationController {

	private final UserRegistrationVerificationService userRegistrationVerificationService;

	public UserRegistrationVerificationController(UserRegistrationVerificationService userRegistrationVerificationService) {
		this.userRegistrationVerificationService = userRegistrationVerificationService;
	}

	@GetMapping("/verify")
	public ResponseEntity<VerificationResult> verifyUser(@RequestParam("uuid") UUID uuid,
			@RequestParam("age") int age) {
		VerificationResult verificationResult = userRegistrationVerificationService
				.verifyUser(uuid, age);
		return ResponseEntity.ok(verificationResult);
	}

}
