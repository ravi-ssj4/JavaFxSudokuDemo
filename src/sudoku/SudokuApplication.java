/**
 * Program Name: SudokuApplication.java
 *
 * Description:
 * this code defines the entry point for a JavaFX-based Sudoku game.
 * When the application starts, it initializes a user interface
 * and sets up the Sudoku game logic
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */

package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.buildLogic.SudokuBuildLogic;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.UserInterfaceImpl;

import java.io.IOException;

// The class SudokuApplication is a JavaFX application because it extends the JavaFX Application class.
public class SudokuApplication extends Application {
        private IUserInterfaceContract.View uiImpl;


        @Override
        //  The Stage object represents the main window of the application. It's provided by JavaFX.
//        This method is a mandatory override when creating a JavaFX application.
//        The start method is the main entry point for all JavaFX applications.
        public void start(Stage primaryStage) throws Exception{
                uiImpl = new UserInterfaceImpl(primaryStage);
                try {
                        SudokuBuildLogic.build(uiImpl);
                } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                }
        }
        public static void main(String[]args){
                // the launch(args) method is called, which belongs to the JavaFX Application class
                // This method launches the JavaFX application; it will internally call the start method
                launch(args);
        }

}
