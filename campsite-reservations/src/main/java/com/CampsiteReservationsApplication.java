package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.service", "com.controller", "com.repository", "com.config"})
public class CampsiteReservationsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CampsiteReservationsApplication.class, args);
  }
}
