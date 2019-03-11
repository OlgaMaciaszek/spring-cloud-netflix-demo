package io.github.olgamaciaszek.userservice.registration;

import io.github.olgamaciaszek.userservice.dto.UserDto;
import io.github.olgamaciaszek.userservice.model.User;
import io.github.olgamaciaszek.userservice.registration.verification.VerificationResult;
import io.github.olgamaciaszek.userservice.registration.verification.VerificationServiceClient;

import org.springframework.stereotype.Service;

import static io.github.olgamaciaszek.userservice.registration.verification.VerificationResult.Status.VERIFICATION_PASSED;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
public class UserRegistrationService {

	private final VerificationServiceClient verificationServiceClient;

	public UserRegistrationService(VerificationServiceClient verificationServiceClient) {
		this.verificationServiceClient = verificationServiceClient;
	}

	public User registerUser(UserDto userDto) {
		User user = new User(userDto);
		verifyUser(user);
		return user;
	}

	private void verifyUser(User user) {
		VerificationResult verificationResult = verificationServiceClient
				.verifyNewUser(user.getUuid(), user.getAge());
		if (VERIFICATION_PASSED.equals(verificationResult.getStatus())) {
			user.setStatus(User.Status.OK);
		}
		else {
			user.setStatus(User.Status.FRAUD);
		}
	}
}
