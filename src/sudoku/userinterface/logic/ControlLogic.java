/**
 * Program Name: ControlLogic.java
 *
 * Description:
 * ControlLogic serves as a mediator between the game's backend logic and the frontend UI.
 * It responds to UI events, processes the game logic, and updates both the storage and the UI accordingly.
 * The use of interfaces for both storage and UI interactions ensures modularity and flexibility in the design
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */

package sudoku.userinterface.logic;

import sudoku.computationLogic.GameLogic;
import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;

import java.io.IOException;

public class ControlLogic implements IUserInterfaceContract.EventListener {

    /*
    * Very important rule of thumb:
    *   always connect with Views (frontend) or backend from the control logic class
    *   Via INTERFACES !!!! -> COMES FROM SOLID PRINCIPLES
    *  => helps design the application upfront without worrying about the implementation
    * of frontend and backend
    */
    // connecting with backend via Interface
    private IStorage storage;

    // connecting with frontend via Interface
    private IUserInterfaceContract.View view;

    /* The constructor initializes the ControlLogic object with implementations of IStorage and
    IUserInterfaceContract.View. This is an example of Dependency Injection, where the dependencies
    are provided (or injected) into the class, rather than the class creating its own dependencies */
    // the object of this class is created in SudokuBuildLogic and it already has
    // locaded storage and view objects passed here, ready to use
    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;

            // if the input is successfully written to the newGridState matrix,
            // create a new SudokuGame object. why? its a good practice! how?
            // doubt! -> immutable object? create a new data model from an old one every single time
            gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState);

            storage.updateGameData(gameData);
            view.updateSquare(x, y, input);

            if (gameData.getGameState() == GameState.COMPLETE){
                view.showDialog(Messages.GAME_COMPLETE);
            }


        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try {
            storage.updateGameData(GameLogic.getNewGame());

            view.updateBoard(storage.getGameData());

        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }
}
