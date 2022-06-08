package com.usk.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HclGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HclGatewayApplication.class, args);
	}

}
