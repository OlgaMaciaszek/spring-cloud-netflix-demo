package io.github.olgamaciaszek.demoservice;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// for rest template - for web client use `@LoadBalancedClient`
// Ribbon is deprecated!
@RibbonClient(name = "card-service",
		configuration = LoadBalancedConfiguration.class)
// feign
@EnableFeignClients
public class DemoServiceApplication implements CommandLineRunner {

	public static void main(String[] args) { SpringApplication.run(DemoServiceApplication.class, args);
	}

	@Autowired MyService myService;
	@Autowired MyLoadBalancedService myLoadBalancedService;
	@Autowired MyFeignClient myFeignClient;

	@Override
	public void run(String... args) throws Exception {
//		myService.print();
//		myService.call();
//		myLoadBalancedService.callLoadBalanced();
//		myLoadBalancedService.callLoadBalanced();
//		myLoadBalancedService.callLoadBalanced();
//		myLoadBalancedService.callLoadBalanced();
//		myService.callLoadBalanced();
//		myService.callLoadBalanced();
//		myService.callLoadBalanced();
//		myService.callLoadBalanced();
		myFeignClient.callFeign();
		myFeignClient.callFeign();
		myFeignClient.callFeign();
		myFeignClient.callFeign();
	}
}

@Configuration
class DemoServiceConfiguration {

	// config-server
	/*@Bean
	MyService myService(@Value("${some-important-text:world}") String value) {
		return new MyService(value);
	}*/

	// discovery-client
	/*@Bean
	MyService myService(@Value("${some-important-text:world}") String value, DiscoveryClient discoveryClient) {
		return new MyService(value, discoveryClient);
	}*/

	// Loadbalanced rest template
	@Bean
	MyService myService(@Value("${some-important-text:world}") String value, DiscoveryClient discoveryClient) {
		return new MyService(value, discoveryClient, restTemplate());
	}

	@Bean
	MyLoadBalancedService myLoadBalancedService(LoadBalancerClient loadBalancerClient) {
		return new MyLoadBalancedService(loadBalancerClient);
	}

	@Bean
	MyFeignClient myFeignClient(CardServiceClient cardServiceClient) {
		return new MyFeignClient(cardServiceClient);
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

class MyService {

	private final String message;
	private final DiscoveryClient discoveryClient;
	private final RestTemplate restTemplate;

	MyService(String message, DiscoveryClient discoveryClient, RestTemplate restTemplate) {
		this.message = message;
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
	}

	void print() {
		System.out.println("\n\n\nHello [" + message + "]\n\n\n");
	}

	void call() {
		System.out.println("\n\n CALL() \n\n");
		List<ServiceInstance> instances = discoveryClient.getInstances("card-service");
		System.out.println("\n\n\nINSTANCES: \n\n" + instances
				.stream()
				.map(ServiceInstance::getUri)
				.collect(Collectors.toList())+ "\n\n");
		if (instances.isEmpty()) {
			return;
		}
		String request = "{\n"
				+ "  \"user\": {\n"
				+ "    \"name\": \"Mary\",\n"
				+ "    \"surname\": \"Smith\",\n"
				+ "    \"idNo\": \"ZXC123\",\n"
				+ "    \"dateOfBirth\": \"1984-11-03\"\n"
				+ "  },\n"
				+ "  \"cardCapacity\": 111\n"
				+ "}\n";
		String response = new RestTemplate()
				.exchange(RequestEntity
				.post(URI.create(instances.get(0).getUri().toString() + "/application"))
				.contentType(MediaType.APPLICATION_JSON)
				.body(request), String.class).getBody();
		System.out.println("\n\n\nRESPONSE: [\n" + response + "\n\n]");
	}

	void callLoadBalanced() {
		System.out.println("\n\n CALLLOADBALANCED() \n\n");
		String request = "{\n"
				+ "  \"user\": {\n"
				+ "    \"name\": \"Mary\",\n"
				+ "    \"surname\": \"Smith\",\n"
				+ "    \"idNo\": \"ZXC123\",\n"
				+ "    \"dateOfBirth\": \"1984-11-03\"\n"
				+ "  },\n"
				+ "  \"cardCapacity\": 111\n"
				+ "}\n";
		String response = this.restTemplate
				.exchange(RequestEntity
				.post(URI.create("http://card-service/application"))
				.contentType(MediaType.APPLICATION_JSON)
				.body(request), String.class).getBody();
		System.out.println("\n\n\nRESPONSE: [\n" + response + "\n\n]");
	}
}


class MyLoadBalancedService {

	private final LoadBalancerClient loadBalancerClient;

	MyLoadBalancedService(LoadBalancerClient loadBalancerClient) {
		this.loadBalancerClient = loadBalancerClient;
	}

	void callLoadBalanced() {
		System.out.println("\n\n CALLLOADBALANCED WITH LOAD BALANCER() \n\n");
		String request = "{\n"
				+ "  \"user\": {\n"
				+ "    \"name\": \"Mary\",\n"
				+ "    \"surname\": \"Smith\",\n"
				+ "    \"idNo\": \"ZXC123\",\n"
				+ "    \"dateOfBirth\": \"1984-11-03\"\n"
				+ "  },\n"
				+ "  \"cardCapacity\": 111\n"
				+ "}\n";
		String uri = this.loadBalancerClient.choose("card-service").getUri().toString();
		System.out.println("Calling [" + uri + "]");
		String response = new RestTemplate()
				.exchange(RequestEntity
						.post(URI.create(uri + "/application"))
						.contentType(MediaType.APPLICATION_JSON)
						.body(request), String.class).getBody();
		System.out.println("\n\n\nRESPONSE: [\n" + response + "\n\n]");
	}
}


class MyFeignClient {

	private final CardServiceClient cardServiceClient;

	MyFeignClient(CardServiceClient cardServiceClient) {
		this.cardServiceClient = cardServiceClient;
	}

	void callFeign() {
		System.out.println("\n\n FEIGN() \n\n");
		String request = "{\n"
				+ "  \"user\": {\n"
				+ "    \"name\": \"Mary\",\n"
				+ "    \"surname\": \"Smith\",\n"
				+ "    \"idNo\": \"ZXC123\",\n"
				+ "    \"dateOfBirth\": \"1984-11-03\"\n"
				+ "  },\n"
				+ "  \"cardCapacity\": 111\n"
				+ "}\n";
		String response = this.cardServiceClient.application(request);
		System.out.println("\n\n\nRESPONSE: [\n" + response + "\n\n]");
	}
}

@FeignClient("card-service")
interface CardServiceClient {
	@PostMapping(path = "/application",
			produces = "application/json",
			consumes = "application/json")
	String application(@RequestBody String string);
}