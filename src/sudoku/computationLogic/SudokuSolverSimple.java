/**
 * Program Name: SudokuSolver.java
 *
 * Description:
 * The primary purpose of this class is to determine if a given Sudoku puzzle is solvable.
 * It uses a backtracking approach to do this, which involves trying to fill in
 * the puzzle cell-by-cell, and if a cell is found where no number can be legally
 * placed, it backtracks to a previous cell and tries a different number there
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

public class SudokuSolverSimple {
    /*
    * Algo taken from cornell university website
    * */
    public static boolean puzzleIsSolvable(int[][] puzzle){
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);
        /*
        * These lines initialize the 'index' variable to keep track of which empty cell
        * we're currently trying to fill and 'input' variable for the current number
        * we're trying to place in that empty cell*/
        int index = 0;
        int input = 1;

        while (index < 10) {
            Coordinates current = emptyCells[index];
            input = 1;

            while (input < 40){
                // putting the input = 1 (let's say) into an empty cell
                // and then again checking if the puzzlbe becomes invalid or not
                puzzle[current.getX()][current.getY()] = input;

                if (GameLogic.sudokuIsInvalid(puzzle)){
                    // if we have tried to place all numbers from 0 to 9
                    // but we are still on the first empty cell itself,
                    // means we were not able to place any of the numbers
                    // from 0 to 9 in the first cell itself, no need to check
                    // further
                    if (index == 0 && input == GRID_BOUNDARY){
                        return false;
                    } else if (input == GRID_BOUNDARY) {
                        // in case the current number == 9, then we backtrack, ie. go to the prev
                        // empty cell and try placing 9 there
                        index--;
                    }
                    // if we are here, then we can place the input on the current cell and then check for next
                    // number if that can be placed
                    input++;
                }else {
                    // if sudoku was solvable, then whatever number we put on the prev empty cell, works,
                    // let's do the same thing for next empty cell
                    index++;

                    // if index == 39, means we were able to fill all the empty 39 cells and hence, solve the game!
                    if (index == 39)
                        return true;

                    input = 10;
                }
            }
        }
        return false;
    }

    /*
    * typeWriterEnumerate just converts 2D array into 1D array row-wise(row-order)
    * and stores all the empty cells only into the emptyCells array which is of Coordinate(ie. (x, y)) type array
    * */
    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
        // 1D array that stores coordinates object
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;

        for (int y = 0; y < GRID_BOUNDARY; y++){
            for (int x = 0; x < GRID_BOUNDARY; x++){
                if (puzzle[x][y] == 0){
                    emptyCells[iterator] = new Coordinates(x, y);
                    if (iterator == 39) {
                        return emptyCells;
                    }
                    iterator++;
                }
            }
        }
        return emptyCells;
    }

}
/**
 * Issues with this code:
 *
 * Magic Numbers:
 *          The use of numbers like 10, 40, and 39 without clear explanation
 *          or reasoning can lead to bugs and makes the code hard to understand.
 * Incomplete Backtracking:
 *          The backtracking logic seems to be incomplete and might not work
 *          correctly for all Sudoku puzzles.
 * Potential Array Index Out of Bound:
 *          The typeWriterEnumerate method assumes there will be at most 40
 *          empty cells, which may not always be the case. This can lead to
 *          ArrayIndexOutOfBoundsExceptions if not handled correctly.
 * Loop Boundaries:
 *          The boundaries of the while loops in puzzleIsSolvable
 *          seem arbitrary and don't align with typical Sudoku rules.
 */