/**
 * Program Name: Main.java
 *
 * Description:
 * Using a separate launcher class to start a JavaFX application is a commonly recommended practice,
 * especially for larger projects or when using build and packaging tools. It provides a clear entry point,
 * offers better compatibility and error handling, and can help avoid some of the pitfalls associated with
 * JavaFX's initialization and runtime requirements
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */
package sudoku;

public class Main {
    public static void main(String[] args) {
        SudokuApplication.main(new String[]{});
    }
}
