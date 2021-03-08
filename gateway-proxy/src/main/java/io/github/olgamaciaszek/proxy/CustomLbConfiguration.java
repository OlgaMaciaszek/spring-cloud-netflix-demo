package io.github.olgamaciaszek.proxy;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CustomLbConfiguration {


	@Bean
	ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
		return ServiceInstanceListSupplier.builder()
				.withDiscoveryClient()
				.withRequestBasedStickySession()
				.build(context);
	}
}
