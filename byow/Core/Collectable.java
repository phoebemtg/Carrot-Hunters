package byow.Core;

/**
 * respresents an object that a being can pick up (probably a carrot)
 */
public class Collectable {
    private int val; // how much the object is worth (in terms of lives)
    private Location curr; //The current location of the carrot

    /**
     * Constructs a collectable object
     *
     * @param l the location to place this
     */
    public Collectable(Location l) {
        this.val = 1;
        this.curr = l;
    }

    /**
     * return the value of this collectable
     */
    public int getVal() {
        return val;
    }

    /**
     * changes the value of the object
     */
    public void setVal(int i) {
        this.val = i;
    }

    /**
     * @return current carrot locaiton
     */
    public Location getLocation() {
        return this.curr;
    }
}
