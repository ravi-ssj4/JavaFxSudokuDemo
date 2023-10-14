package sudoku.buildLogic;

import sudoku.computationLogic.GameLogic;
import sudoku.persistence.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

/*
* An object that uses other objects should not also build those objects
* The logic to build those objects should be pulled into a different class or a method (this class)
* */
public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View userInterface) throws IOException{
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try{
            initialState = storage.getGameData();
        } catch(IOException e){
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);

        // our task is to set the listener for action items(tiles in our case)
        // and update the new board with the GameState retrieved from the persisted game
        // probably played and saved previously or if not, then update the board with a new game
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
