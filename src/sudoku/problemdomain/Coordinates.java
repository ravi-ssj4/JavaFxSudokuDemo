/**
 * Program Name: Coordinates.java
 *
 * Description:
 * this class represents a 2D coordinate with utility methods to retrieve its values and
 * check the equality of two Coordinates objects
 *
 * @author Ravi Ranjan
 * @version 1.0.0
 *
 * Dependencies:
 * Java FX
 */
package sudoku.problemdomain;

import java.util.Objects;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /*
    * this: current coordinate object that calls this function
      obj: object passed to compare with the current coordinate object
      if they represent the same x and y coordinate,
        * return true
      else
        * return false
    * */
    @Override
    public boolean equals(Object obj) {

        // in case this and obj are exactly the same object(occupy the same memory location)
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        // in case this and obj are different objects but their x and y values are same,
        // they are still considered the same!
        Coordinates objectToCompareWith = (Coordinates) obj;
        return x == objectToCompareWith.x && y == objectToCompareWith.y;
    }

    /*
    * computes a hash code based on the x and y values. The Objects.hash() method is a utility function
    * that takes in multiple values and generates a combined hash code. This method ensures that two
    * Coordinates objects with the same (x, y) values will have the same hash code, maintaining the general
    * contract of hashCode() when the equals() method is overridden
    * */
    @Override
    public int hashCode() {
        // to generate a unique identifier from these specific coordinates
        return Objects.hash(x, y);
    }
}
