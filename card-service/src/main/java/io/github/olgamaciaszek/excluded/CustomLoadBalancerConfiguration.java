//package io.github.olgamaciaszek.excluded;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerProperties;
//import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
//import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.core.ZonePreferenceServiceInstanceListSupplier;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;
//
///**
// * @author Olga Maciaszek-Sharma
// */
//public class CustomLoadBalancerConfiguration {
//
////
////	@Bean
////	ReactorLoadBalancer<ServiceInstance> reactorLoadBalancer(Environment environment,
////			LoadBalancerClientFactory loadBalancerClientFactory) {
////		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
////		return new TestRoundRobinLoadBalancer(name, loadBalancerClientFactory
////				.getLazyProvider(name, ServiceInstanceListSupplier.class));
////	}
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
//
//}
