package com.example.reward_demo_api.service;

import com.example.reward_demo_api.dto.RewardResponse;
import com.example.reward_demo_api.entity.Customer;
import com.example.reward_demo_api.entity.Transaction;
import com.example.reward_demo_api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RewardServiceTest {
  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private RewardService rewardService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCalculateRewards_WithTransactions() {
    Customer customer = new Customer("John Doe", "john@example.com", "1234567890");

    Transaction t1 = new Transaction(
      LocalDate.of(2023, 10, 1), // date
      120.0,                     // amount
      customer                   // customer
    );

    Transaction t2 = new Transaction(
      LocalDate.of(2023, 10, 15),
      80.0,
      customer
    );

    customer.getTransactions().addAll(Arrays.asList(t1, t2));
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

    List<RewardResponse> rewards = rewardService.calculateRewards();

    assertEquals(1, rewards.size());
    RewardResponse response = rewards.get(0);
    assertEquals(customer.getName(), response.getCustomerName());
    assertEquals(120, response.getTotalPoints());
    assertEquals(1, response.getMonthlyPoints().size());
  }

  @Test
  void testCalculateRewards_NoTransactions() {
    Customer customer = new Customer("Jane Doe", "jane@example.com", "0987654321");
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

    List<RewardResponse> rewards = rewardService.calculateRewards();

    assertEquals(1, rewards.size());
    RewardResponse response = rewards.get(0);
    assertEquals(customer.getName(), response.getCustomerName());
    assertEquals(0, response.getTotalPoints());
    assertEquals(0, response.getMonthlyPoints().size());
  }

  @Test
  void testCalculateRewards_NullTransactions() {
    Customer customer = new Customer("Alice", "alice@example.com", "1122334455");
    customer.setTransactions(null);
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

    List<RewardResponse> rewards = rewardService.calculateRewards();

    assertEquals(1, rewards.size());
    RewardResponse response = rewards.get(0);
    assertEquals(customer.getName(), response.getCustomerName());
    assertEquals(0, response.getTotalPoints());
    assertEquals(0, response.getMonthlyPoints().size());
  }

  @Test
  void testCalculateRewards_AtThresholds() {
    Customer customer = new Customer("Threshold Tester", "test@example.com", "0000000000");
    Transaction t1 = new Transaction(LocalDate.of(2023, 9, 1), 50.0, customer);  // 0 points
    Transaction t2 = new Transaction(LocalDate.of(2023, 9, 2), 100.0, customer); // 50 points
    customer.getTransactions().addAll(Arrays.asList(t1, t2));
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

    List<RewardResponse> rewards = rewardService.calculateRewards();

    assertEquals(1, rewards.size());
    RewardResponse response = rewards.get(0);
    assertEquals(50, response.getTotalPoints());
    assertEquals(1, response.getMonthlyPoints().size());
    assertEquals(50, response.getMonthlyPoints().get("2023-09"));
  }

  @Test
  void testCalculateRewards_MultipleCustomers() {
    Customer c1 = new Customer("A", "a@a.com", "1");
    Customer c2 = new Customer("B", "b@b.com", "2");
    Transaction t1 = new Transaction(LocalDate.of(2023, 8, 1), 120.0, c1); // 90 points
    Transaction t2 = new Transaction(LocalDate.of(2023, 8, 2), 60.0, c2);  // 10 points
    c1.getTransactions().add(t1);
    c2.getTransactions().add(t2);
    when(customerRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

    List<RewardResponse> rewards = rewardService.calculateRewards();

    assertEquals(2, rewards.size());
    assertEquals(90, rewards.get(0).getTotalPoints());
    assertEquals(10, rewards.get(1).getTotalPoints());
  }

  @Test
  void testCalculateRewards_TransactionsDifferentMonths() {
    Customer customer = new Customer("Month Tester", "month@test.com", "333");
    Transaction t1 = new Transaction(LocalDate.of(2023, 7, 15), 120.0, customer); // 90 points
    Transaction t2 = new Transaction(LocalDate.of(2023, 8, 15), 80.0, customer);  // 30 points
    customer.getTransactions().addAll(Arrays.asList(t1, t2));
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

    List<RewardResponse> rewards = rewardService.calculateRewards();

    assertEquals(1, rewards.size());
    RewardResponse response = rewards.get(0);
    assertEquals(120, response.getTotalPoints());
    assertEquals(2, response.getMonthlyPoints().size());
    assertEquals(90, response.getMonthlyPoints().get("2023-07"));
    assertEquals(30, response.getMonthlyPoints().get("2023-08"));
  }

  @Test
  void testCalculateRewards_NegativeTransactionAmount() {
    Customer customer = new Customer("Negative", "neg@test.com", "999");
    Transaction t1 = new Transaction(LocalDate.of(2023, 9, 1), -100.0, customer);
    customer.getTransactions().add(t1);
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
    List<RewardResponse> rewards = rewardService.calculateRewards();
    assertEquals(1, rewards.size());
    assertEquals(0, rewards.get(0).getTotalPoints());
  }

  @Test
  void testCalculateRewards_MultipleTransactionsSameMonth() {
    Customer customer = new Customer("MultiMonth", "multi@test.com", "888");
    Transaction t1 = new Transaction(LocalDate.of(2023, 9, 1), 120.0, customer); // 90
    Transaction t2 = new Transaction(LocalDate.of(2023, 9, 2), 80.0, customer);  // 30
    customer.getTransactions().addAll(Arrays.asList(t1, t2));
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
    List<RewardResponse> rewards = rewardService.calculateRewards();
    assertEquals(1, rewards.size());
    assertEquals(120, rewards.get(0).getTotalPoints());
    assertEquals(120, rewards.get(0).getMonthlyPoints().get("2023-09"));
  }

  @Test
  void testCalculateRewards_CustomerWithNullTransactions() {
    Customer customer = new Customer("NullTrans", "null@test.com", "777");
    customer.setTransactions(null);
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
    List<RewardResponse> rewards = rewardService.calculateRewards();
    assertEquals(1, rewards.size());
    assertEquals(0, rewards.get(0).getTotalPoints());
  }

  @Test
  void testCalculateRewards_CustomerWithEmptyTransactions() {
    Customer customer = new Customer("EmptyTrans", "empty@test.com", "666");
    when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
    List<RewardResponse> rewards = rewardService.calculateRewards();
    assertEquals(1, rewards.size());
    assertEquals(0, rewards.get(0).getTotalPoints());
  }

  @Test
  void testCalculateRewards_NoCustomers_ThrowsException() {
    when(customerRepository.findAll()).thenReturn(Collections.emptyList());
    assertThrows(IllegalStateException.class, () -> rewardService.calculateRewards());
  }
}