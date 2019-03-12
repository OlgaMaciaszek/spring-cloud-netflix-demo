package io.github.olgamaciaszek.cardservice.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.olgamaciaszek.cardservice.user.User;
import io.github.olgamaciaszek.cardservice.user.UserServiceClient;
import io.github.olgamaciaszek.cardservice.verification.VerificationApplication;
import io.github.olgamaciaszek.cardservice.verification.VerificationResult;
import io.github.olgamaciaszek.cardservice.verification.VerificationServiceClient;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
public class CardApplicationService {

	private final UserServiceClient userServiceClient;
	private final VerificationServiceClient verificationServiceClient;

	public CardApplicationService(UserServiceClient userServiceClient,
			VerificationServiceClient verificationServiceClient) {
		this.userServiceClient = userServiceClient;
		this.verificationServiceClient = verificationServiceClient;
	}

	public Mono<ApplicationResult> registerApplication(CardApplicationDto applicationDTO) {
		return userServiceClient.registerUser(applicationDTO.user)
				.map(createdUser -> new CardApplication(UUID.randomUUID(),
						createdUser, applicationDTO.cardCapacity)
				)
				.flatMap(this::verifyApplication);
	}

	private Mono<ApplicationResult> verifyApplication(CardApplication application) {
		return verificationServiceClient
				.verify(new VerificationApplication(application.getUuid(),
						application.getCardCapacity()))
				.map(verificationResult -> updateApplication(verificationResult,
						application));
	}

	private ApplicationResult updateApplication(VerificationResult verificationResult,
			CardApplication application) {
		if (!VerificationResult.Status.VERIFICATION_PASSED
				.equals(verificationResult.status)
				|| !User.Status.OK.equals(application.getUser().getStatus())) {
			application.setApplicationResult(ApplicationResult.rejected());
		}
		else {
			application.setApplicationResult(ApplicationResult.granted());
		}
		return application.getApplicationResult();
	}
}
