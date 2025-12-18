package com.springboot.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  // To allow async event listeners
public class OrdersApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}
}
