package io.github.olgamaciaszek.excluded;

import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;

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


}
