package io.github.olgamaciaszek.cardservice.user;

import java.util.UUID;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

/**
 * @author Olga Maciaszek-Sharma
 */
public class User {

	public UUID uuid;

	public Status status;

	public User() {

	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setStatus(Status status) {
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
		FRAUD;

		Status() {
		}
	}
}

class UserRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection().registerType(TypeReference.of(User.class),
				hint -> hint.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
						MemberCategory.DECLARED_FIELDS));
	}
}
