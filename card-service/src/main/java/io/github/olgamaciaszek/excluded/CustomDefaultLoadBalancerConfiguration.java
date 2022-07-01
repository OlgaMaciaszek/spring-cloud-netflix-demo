package io.github.olgamaciaszek.excluded;

import io.github.olgamaciaszek.cardservice.config.TestRoundRobinLoadBalancer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CustomDefaultLoadBalancerConfiguration {

	@Bean
	ReactorLoadBalancer<ServiceInstance> reactorLoadBalancer(Environment environment,
			LoadBalancerClientFactory loadBalancerClientFactory) {
		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
		return new TestRoundRobinLoadBalancer(loadBalancerClientFactory
				.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
	}

	@Bean
	ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context, @Qualifier("restTemplate") RestTemplate restTemplate) {
		return ServiceInstanceListSupplier
				.builder()
				.withBlockingDiscoveryClient()
				.withBlockingHealthChecks(restTemplate)
				.build(context);
	}

//	@Bean
//	ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
//		return ServiceInstanceListSupplier
//				.builder()
//				.withDiscoveryClient()
//				.withRetryAwareness(ServiceInstanceListSupplier.builder()
//						.withDiscoveryClient()
//						.withHints()
//						.build(context))
//				.build(context);
//	}

}

