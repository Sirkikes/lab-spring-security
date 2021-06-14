package com.item.service.app;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Bean("clientRest")
	@LoadBalanced
	public RestTemplate registRestTemplate(){
		return new RestTemplate();
	}

}
