package com.example.reward_demo_api.controller;


import com.example.reward_demo_api.dto.RewardResponse;
import com.example.reward_demo_api.service.RewardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@AllArgsConstructor
public class RewardController {
  private final RewardService rewardService;

  @GetMapping
  public List<RewardResponse> getRewards() {
    return rewardService.calculateRewards();
  }

}
