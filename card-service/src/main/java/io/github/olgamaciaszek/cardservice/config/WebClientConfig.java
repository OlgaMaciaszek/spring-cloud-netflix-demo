package io.github.olgamaciaszek.cardservice.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
public class WebClientConfig {

	@Bean
	@LoadBalanced
	@Qualifier("loadBalancedWebClient")
	WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	@Qualifier("webClient")
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	public MeterFilter lbRequestsSuccessMeterFilter() {
		return new MeterFilter() {
			@Override
			public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig
					config) {
				if (id.getName().startsWith("loadbalancer.requests.success")) {
					return DistributionStatisticConfig.builder()
							.percentilesHistogram(true).minimumExpectedValue(0.23)
							.percentiles(0.95).build().merge(config);
				}
				return config;
			}
		};
	}
}



