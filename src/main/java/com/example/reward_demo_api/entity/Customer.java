package com.example.reward_demo_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String phone;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Transaction> transactions;

  public Customer() {}
  public Customer(String name, String email, String phone) {
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.transactions = new ArrayList<>();
  }
}
