package com.example.reward_demo_api.repository;

import com.example.reward_demo_api.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void testSaveAndFindById() {
    Customer customer = new Customer("RepoTest", "repo@test.com", "12345");
    Customer saved = customerRepository.save(customer);

    Optional<Customer> found = customerRepository.findById(saved.getId());
    assertTrue(found.isPresent());
    assertEquals("RepoTest", found.get().getName());
  }

  @Test
  void testFindAll_Empty() {
    customerRepository.deleteAll();
    assertTrue(customerRepository.findAll().isEmpty());
  }
}