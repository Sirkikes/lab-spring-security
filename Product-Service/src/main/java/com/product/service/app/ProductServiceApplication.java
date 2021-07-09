package com.product.service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableEurekaClient
//@EnableDiscoveryClient
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan({"com.commons.service.app.entity"})
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
