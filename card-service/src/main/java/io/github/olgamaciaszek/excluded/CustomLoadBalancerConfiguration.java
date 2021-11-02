package io.github.olgamaciaszek.excluded;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CustomLoadBalancerConfiguration {

//	@Bean
//	ReactorLoadBalancer<ServiceInstance> reactorLoadBalancer(Environment environment,
//			LoadBalancerClientFactory loadBalancerClientFactory) {
//		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//		return new TestRoundRobinLoadBalancer(name, loadBalancerClientFactory
//				.getLazyProvider(name, ServiceInstanceSupplier.class));
//	}

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
