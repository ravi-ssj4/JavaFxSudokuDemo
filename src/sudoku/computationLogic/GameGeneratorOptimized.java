package sudoku.computationLogic;

import java.util.Random;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGeneratorOptimized {
    private static Random random = new Random(System.currentTimeMillis());

    public static int[][] getNewGameGrid() {
        int[][] solvedGame = getSolvedGame();
        System.out.println("DEBUG: got the solved game from scratch");
        return unsolveGame(solvedGame);
    }

    private static int[][] unsolveGame(int[][] solvedGame) {
        System.out.println("DEBUG: unsolveGame starts");
        int[][] puzzle = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        SudokuUtilities.copySudokuArrayValues(solvedGame, puzzle);

        int cellsToRemove = 10;  // You can adjust this value as needed
        while (cellsToRemove > 0) {
            int x = random.nextInt(GRID_BOUNDARY);
            int y = random.nextInt(GRID_BOUNDARY);

            if (puzzle[x][y] != 0) {
                int removedValue = puzzle[x][y];
                puzzle[x][y] = 0;

                if (!GameLogic.sudokuIsInvalid(puzzle) &&
                        !SudokuSolverOptimized.hasMultipleSolutions(puzzle)) {
                    cellsToRemove--;
                } else {
                    puzzle[x][y] = removedValue;  // Restore value if multiple solutions are possible
                }
            }
        }
        System.out.println("DEBUG: unsolveGame ends");
        return puzzle;
    }

//    private static int[][] unsolveGame(int[][] solvedGame) {
//        System.out.println("DEBUG: unsolveGame starts");
//        int[][] puzzle = new int[GRID_BOUNDARY][GRID_BOUNDARY];
//        SudokuUtilities.copySudokuArrayValues(solvedGame, puzzle);
//
//        int cellsToRemovePerBox = 40 / 9;  // Assuming 9 boxes in standard Sudoku
//
//        for (int box = 0; box < 9; box++) {
//            int cellsRemovedFromThisBox = 0;
//            while (cellsRemovedFromThisBox < cellsToRemovePerBox) {
//                int x = box / 3 * 3 + random.nextInt(3);
//                int y = box % 3 * 3 + random.nextInt(3);
//
//                if (puzzle[x][y] != 0) {
//                    int removedValue = puzzle[x][y];
//                    puzzle[x][y] = 0;
//
//                    if (!GameLogic.sudokuIsInvalid(puzzle) &&
//                            !SudokuSolverOptimized.hasMultipleSolutions(puzzle)) {
//                        cellsRemovedFromThisBox++;
//                    } else {
//                        puzzle[x][y] = removedValue;  // Restore value if multiple solutions are possible
//                    }
//                }
//            }
//        }
//
//        return puzzle;
//    }

    private static int[][] getSolvedGame() {
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        SudokuSolverOptimized.fillGrid(newGrid);
        return newGrid;
    }
}
