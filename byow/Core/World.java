package byow.Core;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;
/**
 * The World Class Stores rooms and hallways, which are all connected.
 */

/**
 * @source https://stackoverflow.com/questions/5271598/
 * java-generate-random-number-between-two-given-values ?
 */
public class World {
    private Random r;
    private WeightedQuickUnionUF wqu;
    int ROOM_LOAD; //Max Number of rooms
    int WIDTH_LOAD; //Max Width of rooms
    int HEIGHT_LOAD; //Max Height of rooms
    double WALKABLE_LOAD; //Max Ratio of room / empty space

    int WORLD_WIDTH; //The width of the entire world
    int WORLD_HEIGHT; //The height of the entire world
    long SEED;

    List<Walkable> rooms; //A list of all the rooms in the world
    List<Walkable> hallways; //A list of all the locations of hallways in the world.
    List<Location> walkableLocations; //a list of every valid walkable location without repeats
    List<Location> walkableEncounter;
    List<Location> walls;
    List<Location> wallEncounters;
    Location escape;
    List<Location> traps;
    HashSet<Collectable> carrots;
    HashMap<Location, Collectable> carrotLocations;
    Location winningDoor;
    private int CARROT_LOAD = 10;

    Player player;
    Duck duck;
    boolean encounter;
    boolean won;
    boolean running = false;

    /**
     * generates a new random world that is width by HEIGHT
     **/
    public World(long seed, int w, int h) {
        startWorld(seed, w, h);
    }

    public void startWorld(long seed, int w, int h) {
        running = true;
        initialize(w, h, seed); //initialize all local vars

        generateRooms();

        wqu = new WeightedQuickUnionUF(rooms.size()); //This WQU verifies rooms are connected

        while (!connected()) {
            connectRooms();
        }

        combine(rooms); //Add room locations into location list
        combine(hallways); //Add hallway locations into location list
        Wall wall = new Wall(walkableLocations);
        walls = wall.getLocations();
        initializePlayers();
        initializeEncounter(w, h);
        winningDoor = randomLocation(walkableLocations);
        dropObjects();
    }

    /**
     * Generates a random number of rooms that don't overlap
     **/
    private void generateRooms() {

        while (walkableLocations.size() < WALKABLE_LOAD) {
            Room currRoom = createRandomRoom();
            if (currRoom != null) {
                rooms.add(currRoom);
                occupyLocations(currRoom);
            }
        }
    }

    /* CreateRandomRoom generates a random width and height based on the
     * respective loads, a currently unWalkable bottom left start index, and
     * creates a room. */
    private Room createRandomRoom() {
        int width = getRandom(3, WIDTH_LOAD);
        int height = getRandom(3, HEIGHT_LOAD);
        Location bottomLeft = genStartIndex();
        Room tempRoom = new Room(width, height, bottomLeft);
        Verify validPlacement = new Verify(tempRoom, WORLD_HEIGHT, WORLD_WIDTH, walkableLocations);
        if (validPlacement.verified) {
            return tempRoom;
        }
        return null;
    }

    /* GenStartIndex returns a Location object at an unWalkable location,
     * representing the bottomLeft hand corner of a room */
    private Location genStartIndex() {
        int startX = getRandom(1, WORLD_WIDTH);
        int startY = getRandom(1, WORLD_HEIGHT);
        Location ptlLocation = new Location(startX, startY);
        if (walkableLocations.contains(ptlLocation)) {
            return genStartIndex();
        } else {
            return ptlLocation;
        }
    }

    /**
     * adds all occupied locations in list of w without duplicates to walkableLocations array
     */
    public void combine(List<Walkable> w) {
        for (Walkable room : w) {
            List<Location> l = room.getOccupied();
            for (Location loc : l) {
                if (!walkableLocations.contains(loc)) {
                    walkableLocations.add(loc);
                }
            }
        }
    }

    /**
     * Adds all occupied locations to the walkable locations array
     **/
    private void occupyLocations(Walkable w) {
        List<Location> locations = w.getOccupied();
        for (Location coord : locations) {
            walkableLocations.add(coord);
        }
    }

    /**
     * checks if all rooms have been connected via hallways
     */
    private boolean connected() {
        for (int i = 0; i < rooms.size(); i++) {
            //if all rooms are connected to room 0, then all rooms are connected
            if (!wqu.connected(0, i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initialize all instance vars for world?
     */
    public void initialize(int w, int h, long seed) {
        this.WORLD_WIDTH = w;
        this.WORLD_HEIGHT = h;
        this.WALKABLE_LOAD = (WORLD_HEIGHT * WORLD_WIDTH) * 0.1;
        r = new Random(seed);

        rooms = new ArrayList<>();
        hallways = new ArrayList<>();
        walls = new ArrayList<>();
        walkableLocations = new ArrayList<>();
        carrots = new HashSet<>();
        carrotLocations = new HashMap<>();
        encounter = false;
        won = false;

        this.SEED = seed;

        ROOM_LOAD = 10;
        WIDTH_LOAD = 8;
        HEIGHT_LOAD = 8;
    }

    public void initializeEncounter(int w, int h) {
        walkableEncounter = new ArrayList<>();
        wallEncounters = new ArrayList<>();
        for (int i = 1; i < w - 1; i++) {
            for (int z = 1; z < h - 1; z++) {
                walkableEncounter.add(new Location(i, z));
            }
        }
        //top and bottom row:
        for (int i = 0; i < w; i++) {
            wallEncounters.add(new Location(i, 0));
            wallEncounters.add(new Location(i, h - 1));
        }
        //side edges:
        for (int i = 0; i < h; i++) {
            wallEncounters.add(new Location(0, i));
            wallEncounters.add(new Location(w - 1, i));
        }
        //random other walls:
        for (int i = 0; i < 200; i++) {
            Location rand = randomLocation(walkableEncounter);
            wallEncounters.add(rand);
            walkableEncounter.remove(rand);
        }
        //escape door:
        escape = randomLocation(walkableEncounter);
        //trap door:
        traps = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            traps.add(randomLocation(walkableEncounter));
        }
    }

    /**
     * initializes the player and duck and places them randomly on the board
     */
    public void initializePlayers() {
        Location startPlayer;
        // Randomly select a location from the walkable list
        int walkableIdx = getRandom(0, walkableLocations.size());
        startPlayer = walkableLocations.get(walkableIdx);
        player = new Player(startPlayer);

        Location startDuck;
        //Randomly select a location from the walkable list
        int walkableIdx2 = getRandom(0, walkableLocations.size());

        //verify the locations of the player and the duck are not the same
        while (walkableIdx == walkableIdx2) {
            walkableIdx2 = getRandom(0, walkableLocations.size());
        }
        startDuck = walkableLocations.get(walkableIdx2);
        duck = new Duck(startDuck);
    }

    /** starts an encounter scene **/
    public void startEncounter() {
        encounter = true;
        Location playerLoc = randomLocation(walkableEncounter);
        Location duckLoc = randomLocation(walkableEncounter);
        player.teleport(playerLoc.getX(), playerLoc.getY());
        duck.teleport(duckLoc.getX(), duckLoc.getY());
    }

    /**
     * Drops 10 carrots throughout the world
     */
    public void dropObjects() {
        for (int i = 0; i < CARROT_LOAD; i++) {
            Collectable c = new Collectable(randomLocation(walkableLocations));
            carrots.add(c);
            carrotLocations.put(c.getLocation(), c);
        }
    }

    /* moves duck to one of the random carrot locations */
    public void moveDuck() {
        int numKeys = carrotLocations.size();
        int randIdx = getRandom(0, numKeys);
        int currIdx = 0;
        for (Location l : carrotLocations.keySet()) {
            if (currIdx == randIdx) {
                duck.teleport(l.getX(), l.getY());
            }
            currIdx += 1;
        }
    }
    /**
     * Randomly Return one of the rooms
     **/
    public Room getRoom() {
        int numRooms = rooms.size();
        int random = getRandom(0, numRooms);
        return (Room) rooms.get(random);
    }

    /**
     * connects rooms via hallway
     **/
    public void connectRooms() {
        Room a = getRoom();
        Room b = getRoom();
        //create a hallway between room a and b if a != b.
        // Otherwise, we will just select the random rooms again.
        if (a != b) {
            //Select a random edge location from both rooms
            Location aEdge = randomEdge(a);
            Location bEdge = randomEdge(b);
            Hallway h = new Hallway(aEdge, bEdge, r);
            hallways.add(h);
            a.addHallway();
            b.addHallway();
            // connects the two rooms in the WQU
            int aArrLocation = rooms.indexOf(a);
            int bArrLocation = rooms.indexOf(b);
            if (!wqu.connected(aArrLocation, bArrLocation)) {
                wqu.union(aArrLocation, bArrLocation);
            }
        } else {
            connectRooms();
        }
    }
    /**
     * Pick up carrots
     **/
    public void checkCarrots() {
        if (carrotLocations.containsKey(player.getLocation())) {
            player.pickUp(carrotLocations.get(player.getLocation()));
            Collectable currCarrot = carrotLocations.get(player.getLocation());
            carrots.remove(currCarrot);
            carrotLocations.remove(player.getLocation());
        }
    }
    /**
     * Returns a random edge location from room
     */
    public Location randomEdge(Room room) {
        List<Location> edges = room.getEdges();
        //randomly select a location from edge list:
        int randomIdx = getRandom(0, edges.size() - 1);
        return edges.get(randomIdx);
    }

    /**
     * returns a random integer between lBound and Ubound. Code used from source above
     */
    public int getRandom(int lBound, int uBound) {
        //This guarantees a positive number in between the bounds
        if (uBound != 0) {
            return r.nextInt(Math.abs(uBound - lBound)) + lBound;
        }
        return 0;
    }

    /**
     * @return a random walkable location
     */
    public Location randomLocation(List<Location> walkables) {
        int walkableIdx = getRandom(0, walkables.size());
        return walkables.get(walkableIdx);
    }

    /** returns list of curr walkable locations */
    public List<Location> getWalkableLocations() {
        if (!encounter) {
            return walkableLocations;
        } else {
            return walkableEncounter;
        }
    }

    /** returns list of curr walkable locations */
    public List<Location> getWalls() {
        if (!encounter) {
            return walls;
        } else {
            return wallEncounters;
        }
    }
    /** resets world after encounter */
    public void reset() {
        Location p = randomLocation(walkableLocations);
        player.teleport(p.getX(), p.getY());
        Location d = randomLocation(walkableLocations);
        duck.teleport(d.getX(), d.getY());
    }
}
