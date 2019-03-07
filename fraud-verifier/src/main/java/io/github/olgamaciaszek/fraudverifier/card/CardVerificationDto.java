package io.github.olgamaciaszek.fraudverifier.card;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CardVerificationDto {


	public UUID userId;
	public BigDecimal cardCapacity;

	public CardVerificationDto(UUID cardApplicationId, BigDecimal cardCapacity) {
		this.userId = cardApplicationId;
		this.cardCapacity = cardCapacity;
	}
}
