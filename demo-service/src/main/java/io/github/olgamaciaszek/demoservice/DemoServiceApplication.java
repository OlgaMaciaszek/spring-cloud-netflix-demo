package io.github.olgamaciaszek.demoservice;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoServiceApplication.class, args);
		//context.getBean(MyService.class).print();
//		context.getBean(MyService.class).call();
		context.getBean(MyService.class).callLoadBalanced();
		System.exit(0);
	}

}

@Configuration
class DemoServiceConfiguration {

	/*@Bean
	MyService myService(@Value("${some-important-text:world}") String value) {
		return new MyService(value);
	}*/

	/*@Bean
	MyService myService(@Value("${some-important-text:world}") String value, DiscoveryClient discoveryClient) {
		return new MyService(value, discoveryClient);
	}*/

	@Bean
	MyService myService(@Value("${some-important-text:world}") String value, DiscoveryClient discoveryClient) {
		return new MyService(value, discoveryClient, restTemplate());
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

