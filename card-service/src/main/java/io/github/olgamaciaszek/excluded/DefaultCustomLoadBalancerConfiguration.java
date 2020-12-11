package io.github.olgamaciaszek.excluded;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
public class DefaultCustomLoadBalancerConfiguration {

	@Bean
	public ServiceInstanceListSupplier healthCheckServiceInstanceListSupplier(
			ConfigurableApplicationContext context,
			@Qualifier("webClient") WebClient.Builder webClientBuilder) {
		return ServiceInstanceListSupplier.builder().withDiscoveryClient()
				.withHealthChecks(webClientBuilder.build()).build(context);
	}
}
