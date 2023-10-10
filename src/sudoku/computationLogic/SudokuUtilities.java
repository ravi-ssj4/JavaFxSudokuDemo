package sudoku.computationLogic;

import sudoku.problemdomain.SudokuGame;

/*
* why are we using static methods here?
* 1. to avoid possible side effects (in terms of functional programming)
* 2. shared mutable state problems (poor man's version of functional programming)
*
* */
public class SudokuUtilities {
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray){
        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray){
        int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
        return newArray;
    }
}

