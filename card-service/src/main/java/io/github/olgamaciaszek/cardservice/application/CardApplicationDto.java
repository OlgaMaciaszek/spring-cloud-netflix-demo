package io.github.olgamaciaszek.cardservice.application;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CardApplicationDto {

	public CardApplicationDto() {
	}

	public UserDto user;
	public BigDecimal cardCapacity;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public BigDecimal getCardCapacity() {
		return cardCapacity;
	}

	public void setCardCapacity(BigDecimal cardCapacity) {
		this.cardCapacity = cardCapacity;
	}
}


