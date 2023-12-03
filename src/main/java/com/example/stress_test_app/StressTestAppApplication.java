package com.example.stress_test_app;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAdminServer
public class StressTestAppApplication {
    public static final int NUMBER_OF_ARRAYS = 5;
    public static final int ARRAY_SIZE = 128 * 128;

    public static void main(String[] args) throws InterruptedException {
//        SpringApplication.run(StressTestAppApplication.class, args);

        ConfigurableApplicationContext context = SpringApplication.run(StressTestAppApplication.class, args);

        long startTime = System.currentTimeMillis();
        StressTestBusinessLogic.stressTest();
        long endTime = System.currentTimeMillis();
        System.out.println("Время вычислений:  " + (endTime - startTime) + "мс");

        // Закрываем контекст Spring Boot Admin Server
        context.close();
    }
}
