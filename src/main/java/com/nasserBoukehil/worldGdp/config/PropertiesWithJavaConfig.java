package com.nasserBoukehil.worldGdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesWithJavaConfig { 

	@Bean
	public static PropertySourcesPlaceholderConfigurer	
		PropertySourcesPlaceholderConfigurer(){
			return new PropertySourcesPlaceholderConfigurer();
	}

}
