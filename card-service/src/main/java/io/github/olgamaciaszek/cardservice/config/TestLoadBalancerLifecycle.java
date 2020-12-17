package io.github.olgamaciaszek.cardservice.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.ResponseData;

/**
 * @author Olga Maciaszek-Sharma
 */
public class TestLoadBalancerLifecycle implements LoadBalancerLifecycle<RequestDataContext, ResponseData, ServiceInstance> {
	@Override
	public void onStart(Request<RequestDataContext> request) {
		System.out.println("Test");
	}

	@Override
	public void onStartRequest(Request<RequestDataContext> request, Response<ServiceInstance> lbResponse) {

	}

	@Override
	public void onComplete(CompletionContext<ResponseData, ServiceInstance, RequestDataContext> completionContext) {
		System.out.println("Test");
	}
}
