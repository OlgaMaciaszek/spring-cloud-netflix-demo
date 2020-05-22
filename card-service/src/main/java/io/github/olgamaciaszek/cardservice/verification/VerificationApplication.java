package io.github.olgamaciaszek.cardservice.verification;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Olga Maciaszek-Sharma
 */
public class VerificationApplication {

	private final UUID userId;
	private final BigDecimal cardCapacity;

	public VerificationApplication(UUID cardApplicationId, BigDecimal cardCapacity) {
		this.userId = cardApplicationId;
		this.cardCapacity = cardCapacity;
	}

	public UUID getUserId() {
		return userId;
	}

	BigDecimal getCardCapacity() {
		return cardCapacity;
	}
}
