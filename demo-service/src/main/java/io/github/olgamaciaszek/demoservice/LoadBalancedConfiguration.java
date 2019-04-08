package io.github.olgamaciaszek.demoservice;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancedConfiguration {

	@Bean
	public IRule ribbonRule() {
		return new MyRoundRobinRule();
	}

	// must be public
	public static class MyRoundRobinRule extends RoundRobinRule {
		@Override
		public Server choose(ILoadBalancer lb, Object key) {
			Server server = super.choose(lb, key);
			System.out.println("\nCalling [" + server.getHostPort()+ "]\n");
			return server;
		}

		@Override
		public Server choose(Object key) {
			return super.choose(key);
		}
	}
}