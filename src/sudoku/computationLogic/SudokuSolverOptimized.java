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
 *          for that cell. If a number makes the puzzle invalid, it backtracks and tries the next number.
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

import sudoku.problemdomain.Coordinates;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolverOptimized {
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

    public static boolean solveOptimized(int[][] grid) {
        System.out.println("DEBUG: solveOptimized starts");
        int[] nextCell = findMostConstrainedCell(grid);

        // If no cell is found, then the puzzle is solved.
        if (nextCell == null) {
            return true;
        }

        int i = nextCell[0];
        int j = nextCell[1];

        for (int num = 1; num <= GRID_BOUNDARY; num++) {
            if (isValid(grid, i, j, num)) {
                grid[i][j] = num;

                if (solveOptimized(grid)) {
                    return true;
                }

                // Backtrack if no solution is found
                grid[i][j] = 0;
            }
        }

        return false;
    }

    private static int[] findMostConstrainedCell(int[][] grid) {
        int[] cell = null;
        int minOptions = GRID_BOUNDARY + 1;  // Initialize with a value greater than max possible options

        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (grid[i][j] == 0) {
                    int options = countPossibleValues(grid, i, j);

                    if (options < minOptions) {
                        minOptions = options;
                        cell = new int[] {i, j};
                    }
                }
            }
        }
        return cell;
    }

    private static int countPossibleValues(int[][] grid, int row, int col) {
        int count = 0;

        for (int num = 1; num <= GRID_BOUNDARY; num++) {
            if (isValid(grid, row, col, num)) {
                count++;
            }
        }
        return count;
    }

    /*
    * Purpose:
    * This method is used to create a valid solved Sudoku grid from scratch.
    * It typically starts with an empty grid and fills it cell-by-cell
    * until a fully solved grid is obtained.
    * Context:
    * It's used in the GameGenerator class to produce a completely solved Sudoku puzzle
    * */
    public static void fillGrid(int[][] grid) {
        System.out.println("DEBUG: fillGrid starts");
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (grid[i][j] == 0) {
                    for (int num = 1; num <= GRID_BOUNDARY; num++) {
                        if (isValid(grid, i, j, num)) {
                            grid[i][j] = num;
                            fillGrid(grid);  // Recursively try to fill the rest of the grid

                            if (isFull(grid)) {
                                return; // If the grid is full, we are done
                            }

                            grid[i][j] = 0;  // Backtrack
                        }
                    }
                    return; // If no number can be placed in this cell, exit
                }
            }
        }
    }

    private static boolean isFull(int[][] grid) {
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
