package byow.Core;

import java.util.List;

public class Verify {
    int globalWidth;
    int globalHeight;
    boolean verified;
    List<Location> walkables;

    public Verify(Room tempRoom, int worldHeight, int worldWidth,
                  List<Location> walkableLocations) {
        globalWidth = worldWidth;
        globalHeight = worldHeight;
        walkables = walkableLocations;
        verified = verifyPlacement(tempRoom);
    }


    /**
     * VERIFIES ROOM PLACEMENT METHODS: 8
     * /** Verifies that the room is in a correct location
     **/
    private boolean verifyPlacement(Room room) {
        return (inWorld(room) && noRoomOverlap(room) && noWallOverlap(room));
    }

    /**
     * Checks if the room is within the bounds of the world
     */
    private boolean inWorld(Room room) {
        boolean inside = true;
        int upperRightX = room.getStart().getX() + room.getWidth();
        int upperRightY = room.getStart().getY() + room.getHeight();
        if (upperRightX > globalWidth - 1) {
            inside = false;
        }
        if (upperRightY > globalHeight - 1) {
            inside = false;
        }
        return inside;
    }

    /* noRoomOverlap returns true when none of the locations to be occupied by
     * that room are currently walkable */
    private boolean noRoomOverlap(Room room) {
        List<Location> occupiedLocations = room.getOccupied();
        for (Location cell : occupiedLocations) {
            if (walkables.contains(cell)) {
                return false;
            }
        }
        return true;
    }

    /* no wall Overlap returns true when there is
     a distance of at least 2 spaces between the rooms */

    private boolean noWallOverlap(Room room) {
        return checkTop(room) && checkBottom(room) && checkLeft(room) && checkRight(room);
    }

    private boolean checkTop(Room room) {
        int lat = room.getStart().getY() + room.getHeight();
        int xCoord = room.getStart().getX();
        if (lat != globalHeight) {
            for (int yAbove = lat + 1; yAbove <= lat + 2; yAbove++) {
                for (int x = xCoord; x <= xCoord + room.getWidth(); x++) {
                    Location temp = new Location(x, yAbove);
                    if (walkables.contains(temp)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkBottom(Room room) {
        int lat = room.getStart().getY();
        int xCoord = room.getStart().getX();
        if (lat != 0) {
            for (int yBelow = lat - 1; yBelow >= lat - 2; yBelow--) {
                for (int x = xCoord; x < xCoord + room.getWidth(); x++) {
                    Location temp = new Location(x, yBelow);
                    if (walkables.contains(temp)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkLeft(Room room) {
        int lon = room.getStart().getX();
        int yCoord = room.getStart().getY();
        if (lon != 0) {
            for (int xLeft = lon - 1; xLeft >= lon - 2; xLeft--) {
                for (int y = yCoord; y < yCoord + room.getHeight(); y++) {
                    Location temp = new Location(xLeft, y);
                    if (walkables.contains(temp)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkRight(Room room) {
        int lon = room.getStart().getX() + room.getWidth();
        int yCoord = room.getStart().getY();
        if (lon != globalWidth) {
            for (int xRight = lon + 1; xRight <= lon + 2; xRight++) {
                for (int y = yCoord; y < yCoord + room.getHeight(); y++) {
                    Location temp = new Location(xRight, y);
                    if (walkables.contains(temp)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
