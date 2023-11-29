package com.example.community.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
  @GetMapping("/health")
  public String health() {
    return "ok";
  }
}
