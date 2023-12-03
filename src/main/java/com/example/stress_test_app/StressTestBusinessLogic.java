package com.example.stress_test_app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StressTestBusinessLogic {
    private static final int SIZE = 5;
    private static final int MAX_ITERATIONS = 5;

    public static void stressTest() {
        int numberOfCPUCores = Runtime.getRuntime().availableProcessors();
        int numberOfThreads = numberOfCPUCores * 2;

        List<List<List<Double>>> threeDimensionalList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                long threadId = Thread.currentThread().getId();
                System.out.println("Поток " + threadId + ": Начало вычислений");
                for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
                    threeDimensionalList.addAll(performIntensiveCalculation());
                    consumeMemory();
                }
                System.out.println("Поток " + threadId + ": Окончание вычислений");
            });
        }

        StressTestUtils.stopExecution(executorService);
        StressTestUtils.saveResultToJson(threeDimensionalList);
    }

    private static List<List<List<Double>>> performIntensiveCalculation() {
        List<List<List<Double>>> result = new ArrayList<>();

        for (int x = 0; x < SIZE; x++) {
            List<List<Double>> twoDimensionalList = new ArrayList<>();

            for (int y = 0; y < SIZE; y++) {
                List<Double> oneDimensionalList = new ArrayList<>();

                for (int z = 0; z < SIZE; z++) {
                    double resultValue = Math.sin(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
                    oneDimensionalList.add(resultValue);
                }

                twoDimensionalList.add(oneDimensionalList);
            }

            result.add(twoDimensionalList);
        }

        return result;
    }

    private static void consumeMemory() {
        Random random = new Random();
        int[] array = new int[StressTestAppApplication.NUMBER_OF_ARRAYS * StressTestAppApplication.ARRAY_SIZE];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(2);
        }
    }
}
