package io.github.olgamaciaszek.proxy.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
public class ProxyConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("users_service_route",
						route -> route.path("/user-service/**")
								.and()
								.method(HttpMethod.POST)
								.filters(filter -> filter.stripPrefix(1))
								.uri("lb://user-service")).build();
	}
}
