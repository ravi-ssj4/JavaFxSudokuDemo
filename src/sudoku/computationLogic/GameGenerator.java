package sudoku.computationLogic;

import sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {
    public static int[][] getNewGameGrid(){
        return unsolveGame(getSolvedGame());
    }

    private static int[][] unsolveGame(int[][] solvedGame) {
        /*
        * Hypothesis: what if we remove almost half the values on the board(ie. around 40)
        * Will the game be unsolved? Will it be solvable?
        * Can't be sure of it!
        *
        * */
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;

        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        // we loop infinitely until the game becomes solvable
        while (solvable == false){
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            int removals = 0;

            while (removals < 40){
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0){
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    removals++;
                }
            }

            // after removal of 40 values, again attempt to solve the board
            // so that we know if the board is solvable or not
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        // at this point we are sure that our solvableArray is having configuration that can be solved!
        return solvableArray;
    }

    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        for(int value = 1; value <= GRID_BOUNDARY; value++){
            // for every value from 1 to 9, we are going to allocation them 9 times
            // Sudoku game has 9 1's, 9 2's, ... 9 9's
            // to keep track how many times a particular value has been allocated(needs to be 9 times)
            int allocations = 0;
            // to keep track of number of attempts to solve the game for current grid
            int interrupt = 0;

            List<Coordinates> allocTracker = new ArrayList<>();

            // to keep track  of number of attempts to solve the game after resetting the entire grid itself everytime
            int attempts = 0;

            while(allocations < GRID_BOUNDARY){
                // taking care of failures
                if (interrupt > 200){
                    // even after 200 attempts, we did not get a valid solution,
                    // erase or reset the positions on the grid that were set previously
                    // in the current iteration
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500){
                        // even after 500 attempts, no valid solution to the game,
                        // reset the entire grid itself and start from beginning
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                // generating random coordinates(position) in the Sudoku Board
                // and trying to assing current value to it
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if(newGrid[xCoordinate][yCoordinate] == 0){
                    newGrid[xCoordinate][yCoordinate] = value;

                    // check if the current move is valid
                    if(GameLogic.sudokuIsInvalid(newGrid)){
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    }else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }

        }

        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}
