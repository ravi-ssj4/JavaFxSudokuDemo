package sudoku.problemdomain;

import java.io.IOException;

// design by contract - design your application's interfaces beforehand: cool way to use interfaces
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
