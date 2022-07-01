package io.github.olgamaciaszek.cardservice;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootApplication
public class CardServiceApplication implements CommandLineRunner {

	@Autowired
	ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(CardServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String, List<Class<?>>> contexts = context.getBeansWithAnnotation(SpringBootApplication.class)
				.values().stream()
				.map(candidate -> candidate.getClass().getPackage().getName())
				.map(this::getContextsForPackage)
				.reduce((baseMap, newMap) -> {
					baseMap.putAll(newMap);
					return baseMap;
				}).orElse(new HashMap<>());
		for (Map.Entry<String, List<Class<?>>> entry : contexts.entrySet()) {
			System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()
					.toArray()));
		}
	}

	private Map<String, List<Class<?>>> getContextsForPackage(String rootPackage) {
		Map<String, List<Class<?>>> contexts = new HashMap<>();
		ClassPathScanningCandidateComponentProvider classScanner = new ClassPathScanningCandidateComponentProvider(false);
		classScanner.addIncludeFilter(new AnnotationTypeFilter(LoadBalancerClients.class));
		Set<BeanDefinition> clientsAnnotatedBeans = new HashSet<>(classScanner.findCandidateComponents(rootPackage));
		for (BeanDefinition beanDefinition : clientsAnnotatedBeans) {
			contexts.putAll(buildLoadBalancerClientsContextMap(beanDefinition));
		}
		classScanner.resetFilters(false);
		classScanner.addIncludeFilter(new AnnotationTypeFilter(LoadBalancerClient.class));
		Set<BeanDefinition> clientAnnotatedBeans = new HashSet<>(classScanner.findCandidateComponents(rootPackage));
		for (BeanDefinition beanDefinition : clientAnnotatedBeans) {
			Map.Entry<String, List<Class<?>>> configEntry = buildLoadBalancerClientContextMap(beanDefinition);
			contexts.put(configEntry.getKey(), configEntry.getValue());
		}
		return contexts;
	}

	private Map.Entry<String, List<Class<?>>> buildLoadBalancerClientContextMap(BeanDefinition beanDefinition) {
		MultiValueMap<String, Object> annotatedAttributes = getAnnotatedAttributes(beanDefinition, LoadBalancerClient.class);
		return toContextConfigMapEntry(annotatedAttributes.toSingleValueMap(), Collections.emptyList());
	}

	private Map<String, List<Class<?>>> buildLoadBalancerClientsContextMap(BeanDefinition beanDefinition) {
		Map<String, Object> annotatedAttributes = getAnnotatedAttributes(beanDefinition, LoadBalancerClients.class).toSingleValueMap();
		Class<?>[] defaultConfigClasses = (Class<?>[]) annotatedAttributes.get("defaultConfiguration");
		Map<String, Object>[] clientContexts = (Map<String, Object>[]) annotatedAttributes.get("value");
		return new HashMap<>(buildLoadBalancerClientsContextMap(clientContexts, Arrays.asList(defaultConfigClasses)));
	}

	private MultiValueMap<String, Object> getAnnotatedAttributes(BeanDefinition beanDefinition, Class<?> annotationClass) {
		if (!(beanDefinition instanceof AnnotatedBeanDefinition annotatedBeanDefinition)) {
			return new LinkedMultiValueMap<>();
		}
		MultiValueMap<String, Object> annotatedAttributes = annotatedBeanDefinition.getMetadata()
				.getAllAnnotationAttributes(annotationClass.getName());
		if (annotatedAttributes == null) {
			return new LinkedMultiValueMap<>();
		}
		return annotatedAttributes;
	}

	private Map<String, List<Class<?>>> buildLoadBalancerClientsContextMap(Map<String, Object>[] clientContexts, List<Class<?>> defaultConfigClasses) {
		return Arrays.stream(clientContexts)
				.map(clientContext -> toContextConfigMapEntry(clientContext, defaultConfigClasses))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private Map.Entry<String, List<Class<?>>> toContextConfigMapEntry(Map<String, Object> clientContext, List<Class<?>> defaultConfigClasses) {
		String contextId = String.valueOf(clientContext.get("value"));
		List<Class<?>> configClasses = Arrays.asList((Class<?>[]) clientContext.get("configuration"));
		List<Class<?>> contextConfig = configClasses.size() > 0 ? configClasses :
				(defaultConfigClasses.size() > 0 ? defaultConfigClasses : Collections.singletonList(LoadBalancerClientConfiguration.class));
		return Map.entry(contextId, contextConfig);
	}

}

