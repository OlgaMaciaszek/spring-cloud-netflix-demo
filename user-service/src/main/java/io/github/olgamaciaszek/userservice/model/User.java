package io.github.olgamaciaszek.userservice.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import io.github.olgamaciaszek.userservice.dto.UserDto;

/**
 * @author Olga Maciaszek-Sharma
 */
public class User {

	private final UUID uuid;
	private final LocalDate dateOfBirth;
	private String name;
	private String surname;
	private String idNo;
	private Status status;

	public User(UserDto userDto) {
		uuid = UUID.randomUUID();
		dateOfBirth = userDto.dateOfBirth;
		name = userDto.name;
		surname = userDto.surname;
		idNo = userDto.idNo;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getIdNo() {
		return idNo;
	}

	public int getAge() {
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

	public enum Status {
		NEW,
		OK,
		FRAUD
	}
}
