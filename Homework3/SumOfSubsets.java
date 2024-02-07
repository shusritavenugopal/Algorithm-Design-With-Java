package com.assignment;
import java.time.Instant;
import java.time.Duration;

public class SumOfSubsets {
    static int count = 0;
    public static void sumOfSubsets(int[] weights, int target) {
        boolean[] solution = new boolean[weights.length];
        sumOfSubsetsHelper(weights, solution, 0, 0, target);
    }

    public static void sumOfSubsetsHelper(int[] weights, boolean[] solution, int currentIndex, int currentSum, int target) {
        if (currentSum == target) {
            // Print the solution
            for (int i = 0; i < solution.length; i++) {
                if (solution[i]) {
                    System.out.print(weights[i] + " ");
                }
            }
            System.out.println();
            count ++;
            return;
        }

        if (count > 0 || currentIndex >= weights.length || currentSum > target) {
            return;
        }

        // Include the current element
        solution[currentIndex] = true;
        sumOfSubsetsHelper(weights, solution, currentIndex + 1, currentSum + weights[currentIndex], target);

        // Exclude the current element
        solution[currentIndex] = false;
        sumOfSubsetsHelper(weights, solution, currentIndex + 1, currentSum, target);
    }

    public static void main(String[] args) {
        int[] weights = {10, 7, 5, 18, 12, 20, 15};
        int target = 35;
        Instant start = Instant.now();
        sumOfSubsets(weights, target);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Time taken when implemeted for single solution item: " + duration.toNanos() + "ns");
    }
}
