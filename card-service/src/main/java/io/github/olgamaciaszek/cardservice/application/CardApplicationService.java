package io.github.olgamaciaszek.cardservice.application;

import java.util.UUID;

import io.github.olgamaciaszek.cardservice.user.User;
import io.github.olgamaciaszek.cardservice.user.UserServiceClient;
import io.github.olgamaciaszek.cardservice.verification.VerificationApplication;
import io.github.olgamaciaszek.cardservice.verification.VerificationResult;
import io.github.olgamaciaszek.cardservice.verification.VerificationServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
class CardApplicationService {

	private static final Log LOG = LogFactory.getLog(CardApplicationService.class);
	private final UserServiceClient userServiceClient;
	private final VerificationServiceClient verificationServiceClient;

	public CardApplicationService(UserServiceClient userServiceClient,
			VerificationServiceClient verificationServiceClient) {
		this.userServiceClient = userServiceClient;
		this.verificationServiceClient = verificationServiceClient;
	}

	public CardApplication registerApplication(CardApplicationDto applicationDTO) {
		User user = userServiceClient.registerUser(applicationDTO.user).getBody();
		LOG.debug("Registered user: " + user);
		CardApplication application = new CardApplication(UUID.randomUUID(),
				user, applicationDTO.cardCapacity);
		if (User.Status.OK != user.getStatus()) {
			application.setApplicationResult(ApplicationResult.rejected());
			return application;
	}
		VerificationResult verificationResult = verificationServiceClient
				.verify(new VerificationApplication(application.getUuid(),
						application.getCardCapacity())).getBody();
		if (!VerificationResult.Status.VERIFICATION_PASSED
				.equals(verificationResult.status)) {
			application.setApplicationResult(ApplicationResult.rejected());
		}
		else {
			application.setApplicationResult(ApplicationResult.granted());
		}
		return application;
	}
}
