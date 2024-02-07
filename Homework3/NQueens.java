package com.assignment;

import java.util.Random;

public class NQueens {
    public static boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    public static double monteCarloEstimate(int n, int numIterations) {
        int validSolutions = 0;
        Random random = new Random();
        for (int iteration = 0; iteration < numIterations; iteration++) {
            int[] board = new int[n];
            boolean foundSolution = true;

            for (int i = 0; i < n; i++) {
                int randomCol = random.nextInt(n);
                board[i] = randomCol;
                if (!isSafe(board, i, randomCol)) {
                    foundSolution = false;
                    break;
                }
            }

            if (foundSolution) {
                validSolutions++;
            }
        }
        return (double) validSolutions / numIterations;
    }

    public static void main(String[] args) {
        int n = 8;
        int numIterations = 20;
        double averageEstimate = 0;

        for (int i = 0; i < numIterations; i++) {
            averageEstimate += monteCarloEstimate(n, numIterations);
        }
        averageEstimate /= numIterations;

        System.out.println("Average Estimate for " + numIterations + " iterations: " + averageEstimate);
    }
}
