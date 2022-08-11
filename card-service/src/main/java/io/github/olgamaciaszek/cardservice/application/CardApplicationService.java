package io.github.olgamaciaszek.cardservice.application;

import java.util.UUID;

import io.github.olgamaciaszek.cardservice.user.User;
import io.github.olgamaciaszek.cardservice.user.UserServiceClient;
import io.github.olgamaciaszek.cardservice.verification.IgnoredServiceClient;
import io.github.olgamaciaszek.cardservice.verification.VerificationApplication;
import io.github.olgamaciaszek.cardservice.verification.VerificationResult;
import io.github.olgamaciaszek.cardservice.verification.VerificationServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
class CardApplicationService {

	private static final Log LOG = LogFactory.getLog(CardApplicationService.class);

	private final UserServiceClient userServiceClient;
	private final VerificationServiceClient verificationServiceClient;
	private final IgnoredServiceClient ignoredServiceClient;

	public CardApplicationService(UserServiceClient userServiceClient,
			VerificationServiceClient verificationServiceClient, IgnoredServiceClient ignoredServiceClient) {
		this.userServiceClient = userServiceClient;
		this.verificationServiceClient = verificationServiceClient;
		this.ignoredServiceClient = ignoredServiceClient;
	}

	Mono<ApplicationResult> registerApplication(CardApplicationDto applicationDTO) {
		return userServiceClient.registerUser(applicationDTO.user)
				.map(createdUser -> new CardApplication(UUID.randomUUID(),
						createdUser, applicationDTO.cardCapacity)
				)
				.flatMap(this::verifyApplication);
	}

	private Mono<ApplicationResult> verifyApplication(CardApplication application) {
		return ignoredServiceClient  // calling this to show how injected LB function works
				.callIgnoredService()
				.doOnNext(LOG::info)
				.then(verificationServiceClient  // uses @LoadBalanced WebClient.Builder
						.verify(new VerificationApplication(application.getUuid(),
								application.getCardCapacity()))
						.map(verificationResult -> updateApplication(verificationResult,
								application)));
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
