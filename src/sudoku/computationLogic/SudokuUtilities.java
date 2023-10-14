package sudoku.computationLogic;

import sudoku.problemdomain.SudokuGame;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

/*
* why are we using static methods here?
* 1. to avoid possible side effects (in terms of functional programming)
* 2. shared mutable state problems (poor man's version of functional programming)
*
* */
public class SudokuUtilities {
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray){
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray){
        int[][] newArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
        return newArray;
    }
}

