package io.github.olgamaciaszek.cardservice.application;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CardApplicationDto {

	public User user;
	public BigDecimal cardCapacity;

	public static class User {
		public String name;
		public String surname;
		public String idNo;
		public LocalDate dateOfBirth;
	}
}
