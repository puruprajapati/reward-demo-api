package com.example.reward_demo_api.repository;

import com.example.reward_demo_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
