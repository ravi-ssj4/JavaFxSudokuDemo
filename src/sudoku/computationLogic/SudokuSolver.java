package sudoku.computationLogic;

import sudoku.problemdomain.Coordinates;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {
    public static boolean puzzleIsSolvable(int[][] puzzle){
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

    }

    /*
    * typeWriterEnumerate just converts 2D array into 1D array row-wise(row-order)
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
