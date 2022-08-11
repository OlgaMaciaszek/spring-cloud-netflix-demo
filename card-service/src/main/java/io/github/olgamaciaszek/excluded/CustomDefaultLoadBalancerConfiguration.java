package io.github.olgamaciaszek.excluded;

import io.github.olgamaciaszek.cardservice.config.TestRoundRobinLoadBalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

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
	LoadBalancerLifecycle loadBalancerLifecycle() {
		return new TestLoadBalancerLifecycle();
	}

}
