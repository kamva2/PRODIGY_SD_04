import java.util.Scanner;

public class SudokuSolver {
    private static final int GRID_SIZE = 9;

    public static void main(String[] args) {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Sudoku puzzle row by row (use 0 for empty cells):");

        for (int row = 0; row < GRID_SIZE; row++) {
            while (true) {
                System.out.printf("Enter row %d (9 numbers separated by spaces): ", row + 1);
                String input = scanner.nextLine();
                String[] values = input.split(" ");

                if (values.length == GRID_SIZE) {
                    boolean validRow = true;
                    for (int col = 0; col < GRID_SIZE; col++) {
                        try {
                            int value = Integer.parseInt(values[col]);
                            if (value < 0 || value > 9) {
                                System.out.println("Please enter numbers between 0 and 9.");
                                validRow = false;
                                break;
                            }
                            board[row][col] = value;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter integers only.");
                            validRow = false;
                            break;
                        }
                    }
                    if (validRow) {
                        break;
                    }
                } else {
                    System.out.println("Please enter exactly 9 numbers.");
                }
            }
        }

        if (solveBoard(board)) {
            System.out.println("Sudoku solved successfully!");
        } else {
            System.out.println("Unsolvable board.");
        }

        printBoard(board);
        scanner.close();
    }

    private static boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isValid(board, num, row, col)) {
                            board[row][col] = num;

                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] board, int num, int row, int col) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        int subGridRowStart = row - row % 3;
        int subGridColStart = col - col % 3;
        for (int i = subGridRowStart; i < subGridRowStart + 3; i++) {
            for (int j = subGridColStart; j < subGridColStart + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("-----------");
            }
            for (int col = 0; col < GRID_SIZE; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print("|");
                }
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }
}
