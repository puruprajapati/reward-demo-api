package com.example.reward_demo_api.service;

import com.example.reward_demo_api.entity.Customer;
import com.example.reward_demo_api.entity.Transaction;
import com.example.reward_demo_api.dto.RewardResponse;
import com.example.reward_demo_api.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RewardService {
  private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
  private final CustomerRepository customerRepository;

  public List<RewardResponse> calculateRewards() {
    List<Customer> customers = customerRepository.findAll();
    List<RewardResponse> results = new ArrayList<>();

    if (customers.isEmpty()) {
      throw new IllegalStateException("No customers found to calculate rewards");
    }

    customers.forEach(customer -> {
      List<Transaction> transactions = Optional.ofNullable(customer.getTransactions()).orElse(Collections.emptyList());

      if (transactions.isEmpty()) {
        results.add(new RewardResponse(customer.getId(), customer.getName(), Map.of(), 0));
        return;
      }

      Map<String, Integer> monthlyPoints = transactions.stream()
        .collect(Collectors.groupingBy(
          t -> t.getDate().format(MONTH_FORMATTER),
          Collectors.summingInt(t -> calculatePoints(t.getAmount()))
        ));

      int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

      results.add(new RewardResponse(customer.getId(), customer.getName(), monthlyPoints, totalPoints));
    });

    return results;
  }

  private int calculatePoints(double amount) {
    if (amount <= 50) return 0;
    if (amount <= 100) return (int) (amount - 50);
    return (int) (2 * (amount - 100) + 50);
  }
}
