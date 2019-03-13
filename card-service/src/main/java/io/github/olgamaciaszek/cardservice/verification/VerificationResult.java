package io.github.olgamaciaszek.cardservice.verification;

import java.util.UUID;

/**
 * @author Olga Maciaszek-Sharma
 */
public class VerificationResult {

	private UUID uuid;
	public Status status;

	private VerificationResult(UUID uuid, Status status) {
		this.uuid = uuid;
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

	UUID getUuid() {
		return uuid;
	}

	Status getStatus() {
		return status;
	}

	public enum Status {
		VERIFICATION_PASSED,
		VERIFICATION_FAILED
	}
}
