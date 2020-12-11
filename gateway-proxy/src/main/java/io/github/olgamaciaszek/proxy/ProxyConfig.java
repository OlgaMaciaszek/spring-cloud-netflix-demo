package io.github.olgamaciaszek.proxy;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.ResponseData;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
class ProxyConfig {

	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("users_service_route",
						route -> route.path("/user-service/**")
								.and()
								.method(HttpMethod.POST)
								.filters(filter -> filter.stripPrefix(1)
								)
								.uri("lb://user-service")).build();
	}

	@Bean
	LoadBalancerLifecycle<RequestDataContext, ResponseData, ServiceInstance> testLoadBalancerLifecycle() {
		return new TestLoadBalancerLifecycle();
	}
}
