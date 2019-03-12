package io.github.olgamaciaszek.fraudverifier;

import java.util.UUID;

/**
 * @author Olga Maciaszek-Sharma
 */
public class VerificationResult {

	private UUID userId;
	private Status status;

	private VerificationResult(UUID userId, Status status) {
		this.userId = userId;
		this.status = status;
	}

	public VerificationResult() {
	}

	public static VerificationResult passed(UUID userId) {
		return new VerificationResult(userId, Status.VERIFICATION_PASSED);
	}

	public static VerificationResult failed(UUID userId) {
		return new VerificationResult(userId, Status.VERIFICATION_FAILED);
	}

	public UUID getUserId() {
		return userId;
	}

	public Status getStatus() {
		return status;
	}

	enum Status {
		VERIFICATION_PASSED,
		VERIFICATION_FAILED
	}
}
