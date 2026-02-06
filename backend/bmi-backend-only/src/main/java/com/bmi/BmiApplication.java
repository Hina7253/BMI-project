package com.bmi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BmiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BmiApplication.class, args);
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   BMI Calculator Backend Started! 🚀      ║");
        System.out.println("║   API: http://localhost:8080/api/bmi      ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
    }
}
