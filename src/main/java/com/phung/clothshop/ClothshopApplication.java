package com.phung.clothshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.phung.clothshop.model.Account;
import com.phung.clothshop.model.ERole;

@SpringBootApplication
public class ClothshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothshopApplication.class, args);
		System.out.println("Hello world!");
		Account account = new Account();
		account.setUsername("men1");
		account.setPassword("123");
		account.setERole(ERole.ADMIN);
	}
}
