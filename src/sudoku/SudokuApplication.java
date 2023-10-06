package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.UserInterfaceImpl;

import java.io.IOException;

public class SudokuApplication extends Application {
        private IUserInterfaceContract.View uiImpl;


        @Override
        // Stage obj: simple window given by javafx to modify/configure
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
                launch(args);
        }

}
