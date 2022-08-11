package io.github.olgamaciaszek.cardservice;

import io.netty.buffer.AbstractByteBufAllocator;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.util.ClassUtils;

@ImportRuntimeHints(CardServiceApplication.CardServiceApplicationRuntimeHints.class)
@SpringBootApplication
public class CardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardServiceApplication.class, args);
	}

	static class CardServiceApplicationRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			if (!ClassUtils.isPresent("io.netty.buffer.AbstractByteBuf", classLoader)) {
				return;
			}
			hints.reflection()
					.registerType(TypeReference.of(AbstractByteBufAllocator.class),
							hint -> hint.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
			hints.reflection()
					.registerType(TypeReference.of("io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueProducerFields"),
							hint -> hint.withMembers(MemberCategory.DECLARED_FIELDS));
			hints.reflection()
					.registerType(TypeReference.of("io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueConsumerFields"),
							hint -> hint.withMembers(MemberCategory.DECLARED_FIELDS));
		}
	}

}

