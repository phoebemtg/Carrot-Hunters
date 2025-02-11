package byow.Core;

import java.util.*;

/**
 * A hallway is a walkable location where each occupied location is touched by 2 walls
 */
public class Hallway implements Walkable {
    List<Location> occupied;
    List<Location> edges;
    Location start;
    Location end;
    boolean RIGHT = true; //represents if we are building hallway up and right
    boolean LEFT = false; //represents if we are building our hallway up and left
    Random r;

    /**
     * constructs a hallway that goes from start to end
     */
    public Hallway(Location start, Location end, Random r) {
        occupied = new ArrayList<>();
        edges = new ArrayList<>();
        this.start = start;
        this.end = end;
        edges.add(start);
        edges.add(end);
        this.r = r;

        create();
    }


    /**
     * adds a hallway from start to end
     */
    @Override
    public void create() {
        Location bottom = start;
        Location top = end;
        //set bottom to be the lower location, and top to be the higher location.
        if (start.getY() > end.getY()) {
            top = start;
            bottom = end;
        }
        //If we need to go up and right:
        occupied.add(bottom);
        if (bottom.getX() < top.getX()) {
            construct(RIGHT, top, bottom);
        } else {
            construct(LEFT, top, bottom);
        }
    }

    /**
     * Method constructs a hallway between two points.
     *
     * @param side represents if we are going left or right
     */
    public void construct(Boolean side, Location top, Location bottom) {
        boolean dir = r.nextBoolean(); //if true, go up, if false go to the side
        if (dir) {
            while (bottom.getY() != top.getY()) {
                bottom = new Location(bottom.getX(), bottom.getY() + 1);
                occupied.add(bottom);
            }
            while (bottom.getX() != top.getX()) {
                if (side == RIGHT) {
                    bottom = new Location(bottom.getX() + 1, bottom.getY());
                } else {
                    bottom = new Location(bottom.getX() - 1, bottom.getY());
                }
                occupied.add(bottom);
            }

        } else {
            while (bottom.getX() != top.getX()) {
                if (side == RIGHT) {
                    bottom = new Location(bottom.getX() + 1, bottom.getY());
                } else {
                    bottom = new Location(bottom.getX() - 1, bottom.getY());
                }
                occupied.add(bottom);
            }
            while (bottom.getY() != top.getY()) {
                bottom = new Location(bottom.getX(), bottom.getY() + 1);
                occupied.add(bottom);
            }
        }
    }

    /**
     * returns a list of locations occupied by the hallway
     */
    @Override
    public List<Location> getOccupied() {
        return occupied;
    }

    /**
     * returns a list of the start and end location of the hallway
     */
    @Override
    public List<Location> getEdges() {
        return edges;
    }
}
