/**
 * Program Name: UserInterfaceImpl.java
 *
 * Description:
 * UserInterfaceImpl class is responsible for rendering the Sudoku game's UI using JavaFX,
 * handling user input, and communicating with other parts of the application (like the game logic).
 * It uses various helper methods to create a structured and maintainable codebase for
 * managing the Sudoku board's visualization and interactions
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */

package sudoku.userinterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.util.HashMap;

/* implements two interfaces:
    IUserInterfaceContract.View (defining the expected UI behavior)
    EventHandler<KeyEvent> (for handling keyboard events) */
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

    // if this application had multiple screens, its better to put these
    // into a separate file with members as protected
    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    //distance between window and board
    private static final double BOARD_PADDING = 50;

    private static final double BOARD_X_AND_Y = 576;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Sudoku";

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        // we are going to draw components layer by layer through various helper methods:
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        // reveal the user interface
        stage.show();
    }

    private void drawGridLines(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 3;
            } else{
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y
            );
            // this is how we add any UI element to the root (main div)
            root.getChildren().addAll(
                    verticalLine,
                    horizontalLine
            );

            index++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackground);

    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;

        final int xAndYDelta = 64;

        for (int xIndex = 0; xIndex < 9; xIndex++){
            for (int yIndex = 0; yIndex < 9; yIndex++){
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);

                styleSudokuTile(tile, x, y);

                // to listen for inputs from the user
                // this class UserInterfaceImpl implements EventHandler<KeyEvent>
                // which is an inbuilt javafx EventHandler class,
                // hence we can pass this(as it implements EventHandler)
                // attaching setOnKeyPressed event with the tile object basically
                tile.setOnKeyPressed(this);

                // add this tile to the hashMap previously created
                // key: coordinates of the tiles/text fields, value: tile (SudokuTextField) itself
                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);

                // add the tile to the root
                root.getChildren().add(tile);
            }
        }

    }

    private void styleSudokuTile(SudokuTextField tile, int x, int y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        // Background will be supplied by Sudoku board
        tile.setBackground(Background.EMPTY);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);

        Font titleFont = new Font(43);
        title.setFont(titleFont);

        root.getChildren().add(title);
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        this.stage.setScene(scene);
    }

    // The handle method is from the EventHandler<KeyEvent> interface
    // and processes keyboard events for the Sudoku text fields.
    // When a number key is pressed, it updates the corresponding field.
    // When backspace is pressed, it clears the field.
    @Override
    public void handle(KeyEvent keyEvent) {
        // comes from the javafx's EventHandler class
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
            if (keyEvent.getText().matches("[0-9]")){
                int value = Integer.parseInt(keyEvent.getText());
                handleInput(value, keyEvent.getSource());
            } else if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, keyEvent.getSource());
            } else {
                ((TextField) keyEvent.getSource()).setText("");
            }
        }
        // to make sure that this event is not propagated through the rest of the application
        keyEvent.consume();
    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                                ((SudokuTextField) source).getX(),
                                ((SudokuTextField) source).getY(),
                                value
        );
    }

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));

        String value = Integer.toString(input);

        if (value.equals("0")) value = "";

        // only update the UI elements that need to be updated!
        // instead of redrawing the whole game
        tile.textProperty().setValue(value);

    }

    @Override
    public void updateBoard(SudokuGame game) {
        // update the entire board
        // called when the user restarts, etc the entire game
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                // as long as x, y values are correct, doesn't matter if we create a new Coordinates object
                // to act as the key of the hashMap because it creates the same hash value for that particular (x, y)
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                // get immutable copy -> to protect the SudokuGame object "game"
                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );

                if (value.equals("0")) value = "";

                tile.setText(value);

                //If a given tile has a non-zero value (else case) and the state of the game is GameState.NEW, then mark
                //the tile as read only (disable it). Otherwise (if case), ensure that it is NOT read only (enable it)
                if (game.getGameState() == GameState.NEW) {
                    if (value.equals("")) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK)
            listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();
    }
}
