package io.github.olgamaciaszek.excluded;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CustomLoadBalancerConfiguration {

//
//	@Bean
//	ReactorLoadBalancer<ServiceInstance> reactorLoadBalancer(Environment environment,
//			LoadBalancerClientFactory loadBalancerClientFactory) {
//		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//		return new TestRoundRobinLoadBalancer(name, loadBalancerClientFactory
//				.getLazyProvider(name, ServiceInstanceListSupplier.class));
//	}
//
//	@Bean
//	public ServiceInstanceListSupplier serviceInstanceListSupplier(
//			ReactiveDiscoveryClient discoveryClient, Environment environment,
//			LoadBalancerProperties loadBalancerProperties,
//			ApplicationContext context) {
//		DiscoveryClientServiceInstanceListSupplier firstDelegate = new DiscoveryClientServiceInstanceListSupplier(
//				discoveryClient, environment);
////		ZonePreferenceServiceInstanceListSupplier delegate = new ZonePreferenceServiceInstanceListSupplier(firstDelegate,
////				loadBalancerProperties);
////		ObjectProvider<LoadBalancerCacheManager> cacheManagerProvider = context
////				.getBeanProvider(LoadBalancerCacheManager.class);
////		if (cacheManagerProvider.getIfAvailable() != null) {
////			return new CachingServiceInstanceListSupplier(delegate,
////					cacheManagerProvider.getIfAvailable());
////		}
//		return firstDelegate;
//	}

	@Bean
	ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context, @Qualifier("webClient") WebClient.Builder webClientBuilder) {
		return ServiceInstanceListSupplier
				.builder()
				.withDiscoveryClient()
				.withHealthChecks(webClientBuilder.build())
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
