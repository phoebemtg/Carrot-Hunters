package byow.Core;
/** Class represents the "DUCK". The duck is bad guy in our world.
 * */
public class Duck implements Being {

    private String name;
    private Location currLocation;
    private int lives;
    private int DEFAULT = 5;

    /** creates a duck with 5 lives */
    public Duck(Location l) {
        this.lives = DEFAULT;
        this.currLocation = l;
        this.name = "duck";
    }
    /** rtn name of character */
    @Override
    public String getName() {
        return name;
    }
    /** returns curr location */
    @Override
    public Location getLocation() {
        return currLocation;
    }
    /** Move duck by x positions sideways and y pos up and down **/
    @Override
    public void move(int x, int y) {
        int currX = currLocation.getX();
        int currY = currLocation.getY();

        Location newL = new Location(currX + x, currY + y);
        this.currLocation = newL;
    }

    /** teleports the duck to a random location **/
    @Override
    public void teleport(int x, int y) {
        this.currLocation = new Location(x, y);
    }
}
