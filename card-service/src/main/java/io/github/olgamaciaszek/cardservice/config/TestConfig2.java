package io.github.olgamaciaszek.cardservice.config;

import io.github.olgamaciaszek.excluded.CustomLoadBalancerConfiguration;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@LoadBalancerClient(value = "test2", configuration = CustomLoadBalancerConfiguration.class)
public class TestConfig2 {
}
