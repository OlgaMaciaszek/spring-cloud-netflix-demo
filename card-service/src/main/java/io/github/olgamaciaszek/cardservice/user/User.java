package io.github.olgamaciaszek.cardservice.user;

import java.util.UUID;

/**
 * @author Olga Maciaszek-Sharma
 */
public class User {

	private final UUID uuid;

	private final Status status;

	User(UUID uuid, Status status) {
		this.uuid = uuid;
		this.status = status;
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
