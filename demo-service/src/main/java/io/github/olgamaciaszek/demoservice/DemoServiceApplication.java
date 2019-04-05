package io.github.olgamaciaszek.demoservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class DemoServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoServiceApplication.class, args);
		context.getBean(MyService.class).print();
	}

}

@Configuration
class DemoServiceConfiguration {

	@Bean MyService myService(@Value("${some-important-text:world}") String value) {
		return new MyService(value);
	}
}

@Service
class MyService {

	private final String message;

	MyService(String message) {
		this.message = message;
	}

	void print() {
		System.out.println("Hello [" + message + "]");
	}
}

