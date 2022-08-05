package io.github.olgamaciaszek.cardservice.application;

import java.time.LocalDate;

/**
 * @author Olga Maciaszek-Sharma
 */
public class UserDto {

	public UserDto() {
	}

	public String name;
	public String surname;
	public String idNo;
	public LocalDate dateOfBirth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}