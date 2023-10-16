/**
 * Program Name: GameLogic.java
 *
 * Description:
 * GameLogic class provides essential logic for managing a Sudoku game. It can generate new games,
 * check the current state of a Sudoku grid, validate the Sudoku rules, and check if there are unfilled tiles
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */

package sudoku.computationLogic;

import sudoku.constants.GameState;
import sudoku.problemdomain.SudokuGame;

import java.util.HashSet;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameLogic {
    public static SudokuGame getNewGame(){
        return new SudokuGame(
                GameState.NEW,
                GameGeneratorSimple.getNewGameGrid()
        );
    }
    public static GameState checkForCompletion(int[][] grid){
        if (sudokuIsInvalid(grid))
            return GameState.ACTIVE;
        if (tilesAreNotFilled(grid))
            return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    public static boolean sudokuIsInvalid(int[][] grid) {
        HashSet<Integer>[] rows = new HashSet[GRID_BOUNDARY];
        HashSet<Integer>[] cols = new HashSet[GRID_BOUNDARY];
        HashSet<Integer>[] boxes = new HashSet[GRID_BOUNDARY];
        for (int r = 0; r < GRID_BOUNDARY; r++) {
            rows[r] = new HashSet<Integer>();
            cols[r] = new HashSet<Integer>();
            boxes[r] = new HashSet<Integer>();
        }

        for (int r = 0; r < GRID_BOUNDARY; r++) {
            for (int c = 0; c < GRID_BOUNDARY; c++) {
                int val = grid[r][c];

                // Check the row
                int idx = (r / 3) * 3 + c / 3;
                if (rows[r].contains(val) || cols[c].contains(val) || boxes[idx].contains(val)) {
                    return true;
                }
                rows[r].add(val);
                cols[c].add(val);
                boxes[idx].add(val);
            }
        }
        return false;
    }

    public static boolean tilesAreNotFilled(int[][] grid) {
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
                if (grid[xIndex][yIndex] == 0)
                    return true;
            }
        }
        return false;
    }


}
