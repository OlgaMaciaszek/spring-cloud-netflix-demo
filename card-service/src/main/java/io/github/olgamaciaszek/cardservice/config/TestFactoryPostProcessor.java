package io.github.olgamaciaszek.cardservice.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author Olga Maciaszek-Sharma
 */
public class TestFactoryPostProcessor implements ApplicationListener<ApplicationPreparedEvent> {


	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		GenericApplicationContext context = (GenericApplicationContext) event.getApplicationContext();
//		context.refreshForAotProcessing();


		System.out.println("test");
	}
}
