package byow.Core;

import java.util.ArrayList;
import java.util.List;

/**
 * This room class represents a set of locations within the world.
 * It has a width, a height, and a "start location" to identify the room. This location is
 * the bottom left corner.
 **/
public class Room implements Walkable {
    private int width;
    private int height;
    private Location start; //location used to identify the room.
    private List<Location> occupied;
    private List<Location> edges;
    private int hallways;

    /**
     * Creates a width and height of a room where start is the upper LEFT corner of the room
     */
    public Room(int w, int h, Location startIdx) {
        this.width = w;
        this.height = h;
        this.start = startIdx;
        occupied = new ArrayList<>();
        edges = new ArrayList<>();
        create();
        hallways = 0;
    }
    /** increments the number of hallways attached to the room */
    public void addHallway() {
        hallways += 1;
    }

    public int numHallways() {
        return hallways;
    }

    /**
     * Adds all the proper locations to the occupied array
     * This also adds all the edges of the room to the edge array
     **/
    @Override
    public void create() {
        int x = start.getX();
        int y = start.getY();
        for (int r = x; r < x + width; r++) {
            for (int c = y; c < y + height; c++) {
                Location temp = new Location(r, c);
                occupied.add(temp);
                //check if edge:
                if (r == x || r == x + width - 1 || c == y || c == y + height - 1) {
                    edges.add(temp);
                }
            }
        }
    }
    /* RETRIEVAL UTILITIES */

    /**
     * Returns a list of locations that are present in the room
     */
    @Override
    public List<Location> getOccupied() {
        return occupied;
    }

    /**
     * returns a list of the outer locations that bound the room.  These are called edges
     */
    @Override
    public List<Location> getEdges() {
        return edges;
    }

    /**
     * returns the location of the bottom left corner, the Location we use to differentiate rooms
     */
    public Location getStart() {
        return start;
    }

    /* returns the width of the room */
    public int getWidth() {
        return width;
    }

    /* returns the height of the room */
    public int getHeight() {
        return height;
    }

    /* Phoebe's MAIN TESTING METHOD */
    public static void main(String[] args) {
        Room r = new Room(3, 3, new Location(0, 0));
        for (Location l : r.getOccupied()) {
            System.out.println(l.getX() + " " + l.getY());
        }
        System.out.println("");
        for (Location w : r.getEdges()) {
            System.out.println(w.getX() + " " + w.getY());
        }
    }

}
