package sudoku.problemdomain;

import sudoku.constants.GameState;

import java.io.Serializable;

// implements Serializable just so that the game data file can
// be readable and writeable to the operating system files
public class SudokuGame implements Serializable {
    private final GameState gameState;
    private final int[][] gridState;

    public static final int GRID_BOUNDARY = 9;

    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState() {
        return gameState;
    }

    /* we actually want to make the grid state immutable
     * ie. we are not going to give back gridState directly
     * we are going to give back a copy of grid state so that
     * our sudoku game object is not vulnerable to be messed with */
    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }
}
