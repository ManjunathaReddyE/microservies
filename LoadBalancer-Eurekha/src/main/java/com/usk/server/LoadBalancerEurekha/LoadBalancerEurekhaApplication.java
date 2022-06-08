package com.usk.server.LoadBalancerEurekha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LoadBalancerEurekhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadBalancerEurekhaApplication.class, args);
	}

}
