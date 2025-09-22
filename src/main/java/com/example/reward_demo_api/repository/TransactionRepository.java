package com.example.reward_demo_api.repository;

import com.example.reward_demo_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByCustomerId(Long customerId);
}