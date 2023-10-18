package sudoku.persistence;

import javafx.animation.ScaleTransition;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;

import java.io.*;

public class LocalStorageImpl implements IStorage {
    private static File GAME_DATA = new File(System.getProperty("user.home"), "gamedata.txt");
    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        if (!GAME_DATA.exists()) {
            // Handle the situation when the file doesn't exist.
            // You can either return a default game state or throw an exception.
            System.out.println("DEBUG: before throwing IOException from getGameData");
            throw new IOException("Game Data file does not exist");
        }
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        } catch (ClassNotFoundException e) {
            throw new IOException("File not found");
        }
    }
}
