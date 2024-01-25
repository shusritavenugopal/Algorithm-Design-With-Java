public class NQueensCube {
    private static boolean isValidMove(int i, int j, int k, int di, int dj, int dk, int n) {
        return 0 <= i + di && i + di < n &&
               0 <= j + dj && j + dj < n &&
               0 <= k + dk && k + dk < n &&
               (di != 0 || dj != 0 || dk != 0);
    }

    private static boolean isSafe(int[][][] board, int row, int col, int height, int n) {
        for (int i = 0; i < col; i++) {
            if (board[row][i][height] == 1) {
                return false;
            }
        }

        for (int i = 0; i < row; i++) {
            if (board[i][col][height] == 1) {
                return false;
            }
        }

        for (int i = 0; i < height; i++) {
            if (board[row][col][i] == 1) {
                return false;
            }
        }

        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                for (int dk = -1; dk <= 1; dk++) {
                    for (int i = 1; i < n; i++) {
                        if (isValidMove(row, col, height, di * i, dj * i, dk * i, n) &&
                            board[row + di * i][col + dj * i][height + dk * i] == 1) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private static void solveNQueensUtil(int[][][] board, int col, int height, int n, int[] count) {
        if (col == n) {
            count[0]++;
            return;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isSafe(board, i, j, height, n)) {
                    board[i][j][height] = 1;
                    solveNQueensUtil(board, col + 1, (height + 1) % n, n, count);
                    board[i][j][height] = 0;
                }
            }
        }
    }

    private static int solveNQueens(int n) {
        int[][][] board = new int[n][n][n];
        int[] count = new int[]{0};
        solveNQueensUtil(board, 0, 0, n, count);
        return count[0];
    }

    public static void main(String[] args) {
        for (int n = 2; n <= 5; n++) {
            int count = solveNQueens(n);
            System.out.println("The number of legal queen configurations for n = " + n + " is: " + count);
        }
    }
}
