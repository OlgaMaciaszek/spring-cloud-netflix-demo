package io.github.olgamaciaszek.fraudverifier.card;

import java.math.BigDecimal;

import io.github.olgamaciaszek.fraudverifier.VerificationResult;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
public class CardApplicationVerificationService {

	private static final BigDecimal LIMIT = new BigDecimal("9000");

	public VerificationResult verify(CardVerificationDto cardVerificationDto) {
		if (isOutOfRange(cardVerificationDto.cardCapacity)) {
			return VerificationResult.failed(cardVerificationDto.userId);
		}
		else {
			return VerificationResult.passed(cardVerificationDto.userId);
		}
	}

	private boolean isOutOfRange(BigDecimal cardCapacity) {
		return cardCapacity.compareTo(LIMIT) > 0;
	}
}
