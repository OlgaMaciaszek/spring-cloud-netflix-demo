package io.github.olgamaciaszek.fraudverifier.user;

import java.util.UUID;

import io.github.olgamaciaszek.fraudverifier.VerificationResult;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
public class UserRegistrationVerificationService {

	public VerificationResult verifyUser(UUID uuid, int age) {
		if (age < 18 || age > 99) {
			return VerificationResult.failed(uuid);
		}
		else {
			return VerificationResult.passed(uuid);
		}
	}

}
