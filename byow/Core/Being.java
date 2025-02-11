package byow.Core;

public interface Being {
    String getName();

    /** Returns the current location of the Object */
    Location getLocation();

    /** Moves the character NORTH on the grid**/
    default void moveUp() {
        move(0, 1);
    }

    /** Moves the character SOUTH on the grid**/
    default void moveDown() {
        move(0, -1);
    }

    /** Moves the character EAST on the grid**/
    default void moveRight() {
        move(1, 0);
    }

    /** Moves the character WEST on the grid**/
    default void moveLeft() {
        move(-1, 0);
    }

    void move(int x, int y);

    /** Moves characters location to x, y */
    void teleport(int x, int y);
}
