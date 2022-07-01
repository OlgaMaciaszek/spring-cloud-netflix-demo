package io.github.olgamaciaszek.cardservice.config;

import io.github.olgamaciaszek.excluded.CustomDefaultLoadBalancerConfiguration;
import io.github.olgamaciaszek.excluded.CustomLoadBalancerConfiguration;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@LoadBalancerClients(value = {@LoadBalancerClient(value = "test-x1", configuration = CustomLoadBalancerConfiguration.class),
		@LoadBalancerClient(value = "test-x2")}, defaultConfiguration = {CustomLoadBalancerConfiguration.class, CustomDefaultLoadBalancerConfiguration.class})
public class TestConfig4 {
}
