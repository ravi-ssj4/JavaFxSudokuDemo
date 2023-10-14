package sudoku.problemdomain;

import sudoku.computationLogic.SudokuUtilities;
import sudoku.constants.GameState;

import java.io.Serializable;

/*
* Implementing Serializable means instances of SudokuGame can be serialized (converted to a byte stream)
*  and deserialized (reconstructed from a byte stream). This is useful for saving game states to
* files and reading them back
* */
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
     * our SudokuGame object is not vulnerable to be messed with
     * other parts of the code knowingly or unknowingly. It is also
     * thread-safe and ensures data integrity
    */
    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }
}
