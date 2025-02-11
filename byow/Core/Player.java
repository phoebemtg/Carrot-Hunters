package byow.Core;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class Represents a Player, who has a location
 * on the board and can move up/down left and right
 **/
public class Player implements Being {
    private Location curr;
    private String name;
    int lives;
    int DEFAULT = 0;
    List<Collectable> items;

    public Player(Location start) {
        this.curr = start;
        this.name = "Jeff";
        this.lives = DEFAULT;
        this.items = new ArrayList<>();
    }

    /**
     * Returns the name of the player
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the current grid Location of the character
     **/
    @Override
    public Location getLocation() {
        return this.curr;
    }

    /**
     * Moves the player to x, y location
     */
    public void teleport(int x, int y) {
        this.curr = new Location(x, y);
    }

    /**
     * Player loses a life
     **/
    public void loseALife() {
        this.lives -= 1;
    }

    /**
     * player gains a life
     */
    public void gainALife() {
        this.lives += 1;
    }

    /**
     * returns if player has died
     **/
    public boolean dead() {
        return this.lives < 0;
    }

    /**s
     * Moves the player to the desired location
     **/
    public void move(int x, int y) {
        int currX = curr.getX();
        int currY = curr.getY();

        Location newL = new Location(currX + x, currY + y);
        this.curr = newL;
    }

    /**
     * Player adds collectable c to their items list
     */
    public void pickUp(Collectable c) {
        items.add(c);
        gainALife();
    }

    /**
     * returns a list of collected objects
     */
    public List<Collectable> getCollection() {
        return items;
    }
}
