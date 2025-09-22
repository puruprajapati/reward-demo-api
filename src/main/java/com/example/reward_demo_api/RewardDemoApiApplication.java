package com.example.reward_demo_api;

import com.example.reward_demo_api.entity.Customer;
import com.example.reward_demo_api.entity.Transaction;
import com.example.reward_demo_api.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDate;

@SpringBootApplication
public class RewardDemoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardDemoApiApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(CustomerRepository customerRepository) {
		return args -> {
			Customer john = new Customer("John Doe", "john.doe@example.com", "555-1111");
			Customer niru = new Customer("Niru Lama", "niru.lama@example.com", "555-2222");
			Customer nori = new Customer("Nori Yangchen", "nori.yangchen@example.com", "555-3333");

			// Transactions for John
			john.getTransactions().add(new Transaction(LocalDate.of(2025, 1, 5), 120, john));
			john.getTransactions().add(new Transaction(LocalDate.of(2025, 1, 15), 75, john));
			john.getTransactions().add(new Transaction(LocalDate.of(2025, 2, 10), 220, john));
			john.getTransactions().add(new Transaction(LocalDate.of(2025, 3, 8), 95, john));

			// Transactions for Niru
			niru.getTransactions().add(new Transaction(LocalDate.of(2025, 1, 20), 55, niru));
			niru.getTransactions().add(new Transaction(LocalDate.of(2025, 2, 12), 130, niru));
			niru.getTransactions().add(new Transaction(LocalDate.of(2025, 3, 3), 250, niru));
			niru.getTransactions().add(new Transaction(LocalDate.of(2025, 3, 18), 80, niru));

			// Transactions for Nori
			nori.getTransactions().add(new Transaction(LocalDate.of(2025, 1, 25), 45,  nori));
			nori.getTransactions().add(new Transaction(LocalDate.of(2025, 2, 5), 105,  nori));
			nori.getTransactions().add(new Transaction(LocalDate.of(2025, 2, 20), 300, nori));
			nori.getTransactions().add(new Transaction(LocalDate.of(2025, 3, 7), 150, nori));

			customerRepository.save(john);
			customerRepository.save(niru);
			customerRepository.save(nori);
		};
	}
}
