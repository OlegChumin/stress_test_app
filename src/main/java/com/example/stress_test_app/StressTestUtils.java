package com.example.stress_test_app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class StressTestUtils {
    public static void stopExecution(ExecutorService executorService) {
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Ожидание завершения потоков...");
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    public static void saveResultToJson(List<List<List<Double>>> result) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("threeDimensionalListResult.json"), result);
            System.out.println("Результат успешно сохранен в JSON файл.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при сохранении в файл.");
        }
    }
}
