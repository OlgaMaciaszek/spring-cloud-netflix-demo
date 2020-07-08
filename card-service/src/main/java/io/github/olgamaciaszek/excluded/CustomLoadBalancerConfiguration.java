package io.github.olgamaciaszek.excluded;

import io.github.olgamaciaszek.cardservice.config.TestRoundRobinLoadBalancer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.ClientRequestContext;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CustomLoadBalancerConfiguration {

	@Bean
	ReactorLoadBalancer<ServiceInstance> reactorLoadBalancer(Environment environment,
			LoadBalancerClientFactory loadBalancerClientFactory) {
		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
		return new TestRoundRobinLoadBalancer(name, loadBalancerClientFactory
				.getLazyProvider(name, ServiceInstanceListSupplier.class));
	}

	@Bean
	LoadBalancerLifecycle loadBalancerLifecycle() {
		return new TestLoadBalancerLifecycle();
	}

}

class TestLoadBalancerLifecycle implements LoadBalancerLifecycle<ClientRequestContext, ClientResponse, ServiceInstance> {

	private static final Log LOG = LogFactory.getLog(TestLoadBalancerLifecycle.class);

	@Override
	public boolean supports(Class reqClass, Class responseClass, Class serverTypeClass) {
		return ClientRequestContext.class.isAssignableFrom(reqClass)
				&& ClientResponse.class.isAssignableFrom(responseClass)
				&& ServiceInstance.class.isAssignableFrom(serverTypeClass);
	}

	@Override
	public void onStart(Request<ClientRequestContext> request) {
		LOG.error("REQUEST: " + request);
	}

	@Override
	public void onComplete(CompletionContext<ClientResponse, ServiceInstance> completionContext) {
		LOG.error("COMPLETION CONTEXT: " + completionContext);
	}

}
