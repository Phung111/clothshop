package com.phung.clothshop;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.phung.clothshop.model.account.Account;
import com.phung.clothshop.model.account.ERole;

@SpringBootApplication
public class ClothshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothshopApplication.class, args);
		System.out.println("Hello world!");

		// List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
		// names.stream()
		// .filter(name -> name.startsWith("A"))
		// .forEach(System.out::println);

	}
}
