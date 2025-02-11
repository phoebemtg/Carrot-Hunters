package byow.Core;

/**
 * This class repr. an XY location on a 2D array where X is the row, and Y is the column
 **/
public class Location {
    private int x;
    private int y;

    /**
     * Constructor for new location. Takes in row (x) and column (y)
     **/
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the row/x coordinate
     **/
    public int getX() {
        return this.x;
    }

    /**
     * Returns the column/y coordinate
     **/
    public int getY() {
        return this.y;
    }

    /**
     * Checks if location l is the same location as this location obj
     */
    @Override
    public boolean equals(Object o) {

        if (((Location) o).getX() == this.getX() && ((Location) o).getY() == this.getY()) {
            return true;
        }
        return false;
    }

    /**
     * Not a great hashcode fxn, but guarentees that equal locations have same hashcode.
     */
    @Override
    public int hashCode() {
        return x * y;
    }


}
