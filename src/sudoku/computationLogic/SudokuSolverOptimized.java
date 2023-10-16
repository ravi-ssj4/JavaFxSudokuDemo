/**
 * Program Name: SudokuSolverOptimized.java
 *
 * Description:
 * The primary purpose of this class is to determine if a given Sudoku puzzle is solvable.
 * It uses a backtracking approach to do this, which involves trying to fill in
 * the puzzle cell-by-cell, and if a cell is found where no number can be legally
 * placed, it backtracks to a previous cell and tries a different number there
 *
 *
 * Optimizations:
 * 1. Recursive Backtracking:
 *          The main method puzzleIsSolvable uses a recursive backtracking approach.
 *          It finds the first empty cell, then tries numbers from 1 to GRID_BOUNDARY
 *          for that cell. If a number makes the puzzle invalid, it tries the next number.
 *          If the puzzle remains valid, it recursively attempts to solve the rest of the puzzle.
 *
 * 2. isValid Function:
 *          This function checks whether it's valid to place a specific number in a specific cell.
 *          It checks the row, column, and 3x3 box for that cell to ensure no rules of Sudoku are broken.
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */

package sudoku.computationLogic;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolverOptimized {
//    public static boolean puzzleIsSolvable(int[][] puzzle) {
//        for (int row = 0; row < GRID_BOUNDARY; row++) {
//            for (int col = 0; col < GRID_BOUNDARY; col++) {
//                if (puzzle[row][col] == 0) {
//                    for (int num = 1; num <= GRID_BOUNDARY; num++) {
//                        if (isValid(puzzle, row, col, num)) {
//                            puzzle[row][col] = num;
//                            if (puzzleIsSolvable(puzzle)) {
//                                return true;
//                            } else {
//                                puzzle[row][col] = 0;  // Reset the cell for backtracking
//                            }
//                        }
//                    }
//                    return false;  // Trigger backtracking
//                }
//            }
//        }
//        return true;  // Puzzle solved
//    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        // Check row
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 box
        // first determine where this box starts(ie. which cell -> (startRow, startCol))
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean hasMultipleSolutions(int[][] grid) {
        return puzzleIsSolvable(grid, true);
    }

    private static boolean puzzleIsSolvable(int[][] grid, boolean findMultiple) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        // Find the next empty cell
        outerloop:
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (grid[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break outerloop;
                }
            }
        }

        // If no empty cell remains, then the current configuration is a solution
        if (isEmpty) {
            return true;
        }

        for (int num = 1; num <= GRID_BOUNDARY; num++) {
            if (isValid(grid, row, col, num)) {
                grid[row][col] = num;
                if (puzzleIsSolvable(grid, findMultiple)) {
                    if (findMultiple && puzzleIsSolvable(grid, false)) {
                        // If we're here, it means we've found a second solution
                        return true;
                    } else if (!findMultiple) {
                        return false;
                    }
                }
                grid[row][col] = 0; // Backtrack
            }
        }

        return false;
    }
}
