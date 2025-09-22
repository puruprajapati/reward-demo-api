// src/test/java/com/example/reward_demo_api/controller/RewardControllerIntegrationTest.java
package com.example.reward_demo_api.controller;

import com.example.reward_demo_api.service.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RewardControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RewardService rewardService;

  @Test
  void testGetRewards_ReturnsOk() throws Exception {
    when(rewardService.calculateRewards()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/rewards"))
      .andExpect(status().isOk())
      .andExpect(content().json("[]"));
  }

  @Test
  void testGetRewards_NoCustomers_ThrowsException() throws Exception {
    when(rewardService.calculateRewards()).thenThrow(new IllegalStateException("No customers found to calculate rewards"));

    mockMvc.perform(get("/api/rewards"))
      .andExpect(status().isBadRequest());
  }
}