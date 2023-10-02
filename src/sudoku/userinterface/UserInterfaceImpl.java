package sudoku.userinterface;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent> {

    // like a window
    private final Stage stage;

    // comparable to a html div
    private final Group root;

    // Question: how do we keep track of 81 different text fields?
    // private SudokuTextField one, two, three....
    // we had previously overridden the equals and hashCode methods for the Coordinates
    // also SudokuTextField has x and y values !
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

    // this is like the controller / presenter
//    listener will pass information between the frontend and the backend
    private IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;

    @Override
    public void handle(KeyEvent keyEvent) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void updateSquare(int x, int y, int input) {

    }

    @Override
    public void updateBoard(SudokuGame game) {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void showError(String message) {

    }
}
