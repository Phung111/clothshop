package com.phung.clothshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClothshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothshopApplication.class, args);
		System.out.println("Hello world!");

	}
}
