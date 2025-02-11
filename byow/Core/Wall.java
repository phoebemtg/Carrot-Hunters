package byow.Core;

import java.util.ArrayList;
import java.util.List;

/** Represents a set of locations that touch a walkable region */
public class Wall {
    List<Location> walls;

    /** Constructs wall around all walkable location**/
    public Wall(List<Location> walkableLocations) {
        walls = new ArrayList<>();
        for (Location l : walkableLocations) {
            int ro = l.getX();
            int c = l.getY();
            Location left = new Location(ro - 1, c);
            Location right = new Location(ro + 1, c);
            Location up = new Location(ro, c + 1);
            Location down = new Location(ro, c - 1);
            Location topRight = new Location(ro + 1, c + 1);
            Location topLeft = new Location(ro - 1, c + 1);
            Location bottomRight = new Location(ro + 1, c - 1);
            Location bottomLeft = new Location(ro - 1, c - 1);
            if (!walkableLocations.contains(left)) {
                walls.add(left);
            }
            if (!walkableLocations.contains(right)) {
                walls.add(right);
            }
            if (!walkableLocations.contains(up)) {
                walls.add(up);
            }
            if (!walkableLocations.contains(down)) {
                walls.add(down);
            }
            if (!walkableLocations.contains(topRight)) {
                walls.add(topRight);
            }
            if (!walkableLocations.contains(topLeft)) {
                walls.add(topLeft);
            }
            if (!walkableLocations.contains(bottomLeft)) {
                walls.add(bottomLeft);
            }
            if (!walkableLocations.contains(bottomRight)) {
                walls.add(bottomRight);
            }
        }
    }
    /** return list of all locations that are a part of the wall **/
    public List<Location> getLocations() {
        return walls;
    }
}
