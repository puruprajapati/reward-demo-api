package com.example.reward_demo_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RewardResponse {
  private Long customerId;
  private String customerName;
  private Map<String, Integer> monthlyPoints;
  private int totalPoints;
}
