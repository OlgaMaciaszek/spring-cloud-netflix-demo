package io.github.olgamaciaszek.cardservice.application;

/**
 * @author Olga Maciaszek-Sharma
 */
public class ApplicationResult {

	private Status status;

	private ApplicationResult(Status status) {
		this.status = status;
	}

	public static ApplicationResult granted() {
		return new ApplicationResult(Status.GRANTED);
	}

	public static ApplicationResult rejected() {
		return new ApplicationResult(Status.REJECTED);
	}

	Status getStatus() {
		return status;
	}

	enum Status {
		GRANTED,
		REJECTED
	}
}
