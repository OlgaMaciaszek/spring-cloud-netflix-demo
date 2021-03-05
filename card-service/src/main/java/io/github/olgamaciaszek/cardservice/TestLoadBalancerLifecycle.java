package io.github.olgamaciaszek.cardservice;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * @author Olga Maciaszek-Sharma
 */
public class TestLoadBalancerLifecycle implements LoadBalancerLifecycle<RequestDataContext, ClientResponse, ServiceInstance> {

	@Override
	public void onStart(Request<RequestDataContext> request) {
		System.out.println("test");
	}

	@Override
	public void onStartRequest(Request<RequestDataContext> request, Response<ServiceInstance> lbResponse) {
		System.out.println("test");
	}

	@Override
	public void onComplete(CompletionContext<ClientResponse, ServiceInstance, RequestDataContext> completionContext) {
		System.out.println("test");
	}
}
