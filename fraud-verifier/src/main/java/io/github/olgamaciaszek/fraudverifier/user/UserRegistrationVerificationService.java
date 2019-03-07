package io.github.olgamaciaszek.fraudverifier.user;

import io.github.olgamaciaszek.fraudverifier.VerificationResult;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
public class UserRegistrationVerificationService {

	public VerificationResult verifyUser(UserDto userDto) {
		if (userDto.age < 18 || userDto.age > 99) {
			return VerificationResult.failed(userDto.uuid);
		}
		else {
			return VerificationResult.passed(userDto.uuid);
		}
	}

}
