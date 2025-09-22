package com.example.reward_demo_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate date;
  private double amount;
  private String storeName;
  private String paymentMethod;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  public Transaction() {}
  public Transaction(LocalDate date, double amount, Customer customer) {
    this.date = date;
    this.amount = amount;
    this.customer = customer;
  }
}
