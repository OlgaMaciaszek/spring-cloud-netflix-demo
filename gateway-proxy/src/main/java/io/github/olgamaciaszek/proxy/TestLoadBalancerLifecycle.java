package io.github.olgamaciaszek.proxy;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.server.reactive.ServerHttpResponse;

/**
 * @author Olga Maciaszek-Sharma
 */
public class TestLoadBalancerLifecycle implements LoadBalancerLifecycle<RequestDataContext, ServerHttpResponse, ServiceInstance> {

	@Override
	public void onStart(Request<RequestDataContext> request) {
		System.out.println("test");
	}

	@Override
	public void onStartRequest(Request<RequestDataContext> request, Response<ServiceInstance> lbResponse) {
		System.out.println("test");
	}

	@Override
	public void onComplete(CompletionContext<ServerHttpResponse, ServiceInstance, RequestDataContext> completionContext) {
		System.out.println("test");
	}
}
