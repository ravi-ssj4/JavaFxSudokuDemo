package sudoku.userinterface.logic;

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
            gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState));

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
