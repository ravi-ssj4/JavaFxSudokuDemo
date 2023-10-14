/**
 * Program Name: IUserInterfaceContract.java
 *
 * Description:
 * designed to decouple the Sudoku game's user interface
 * from its underlying logic. By establishing clear contracts using interfaces,
 * different parts of the program can interact with the user interface without
 * needing to know about specific UI implementations.
 * This makes the code more modular and easier to maintain or extend
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */

package sudoku.userinterface;

import sudoku.problemdomain.SudokuGame;

public interface IUserInterfaceContract {
    /* Inside IUserInterfaceContract, there's a nested interface called EventListener.
       This is a contract/expectations for any class that implements this Interface and
       wants to listen for UI events:
            onSudokuInput(int x, int y, int input):
                    Expected to be called when a user provides input for
                    a Sudoku cell located at (x, y) with the value input.
            onDialogClick():
                    Expected to be called when a user clicks on a dialog */
    interface EventListener {
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
    }

    /*  Another nested interface inside IUserInterfaceContract is View.
        This defines contract for the Sudoku game's user interface view.

        Methods inside the View interface are:
            setListener(IUserInterfaceContract.EventListener listener):
                        Allows setting an event listener for the view.
            updateSquare(int x, int y, int input):
                        Updates a specific Sudoku cell at (x, y) with the value input.
            updateBoard(SudokuGame game):
                        Updates the entire board with the state from a SudokuGame instance.
            showDialog(String message):
                        Displays a dialog with the provided message.
            showError(String message):
                        Displays an error message or dialog */
    interface View {

        void setListener(IUserInterfaceContract.EventListener listener);

        void updateSquare(int x, int y, int input);
        void updateBoard(SudokuGame game);
        void showDialog(String message);
        void showError(String message);

    }
}
