package io.github.olgamaciaszek.cardservice.application;

/**
 * @author Olga Maciaszek-Sharma
 */
public class ApplicationResult {

	private Status status;

	private ApplicationResult(Status status) {
		this.status = status;
	}

	public ApplicationResult() {

	}

	public static ApplicationResult granted() {
		return new ApplicationResult(Status.GRANTED);
	}

	public static ApplicationResult rejected() {
		return new ApplicationResult(Status.REJECTED);
	}

	public Status getStatus() {
		return status;
	}

	public enum Status {
		GRANTED,
		REJECTED
	}
}
