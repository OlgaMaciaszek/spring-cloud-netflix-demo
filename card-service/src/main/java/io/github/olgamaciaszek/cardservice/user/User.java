package io.github.olgamaciaszek.cardservice.user;

import java.util.UUID;

/**
 * @author Olga Maciaszek-Sharma
 */
public class User {

	private UUID uuid;

	private Status status;

	User(UUID uuid, Status status) {
		this.uuid = uuid;
		this.status = status;
	}

	public User() {

	}

	public UUID getUuid() {
		return uuid;
	}

	public Status getStatus() {
		return status;
	}

	public enum Status {
		NEW,
		OK,
		FRAUD
	}
}
