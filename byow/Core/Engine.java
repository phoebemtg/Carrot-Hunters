package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class Engine {
    TERenderer ter;

    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    World w;
    TETile[][] world;
    String callSequence;
    boolean quitSave;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        quitSave = false;
        Long seed = runMenu();
        if (seed != -1) { // - 1 indicates that the user wants to load the game.
            world = initialize(seed);
            ter.initialize(WIDTH, HEIGHT + 2);
        } else {
            ArrayList<Character> returnList = retrieveCharacters();
            callSequence += cleanCharSeq(returnList);
            seed = initializeReload(returnList);
            ter.initialize(WIDTH, HEIGHT + 2);
            render(seed, null);
        }
        runProgram(seed);
        if (quitSave) {
            saveWorldToText();
        }
        quitProgram();
    }

    /* should initialize the TERenderer screen and return the seed from the last game
     * for image rendering purposes + running the actual program. */
    public Long initializeReload(ArrayList<Character> lastCalls) {
        RestoreGameState lastGame = new RestoreGameState(lastCalls, WIDTH, HEIGHT);
        ter = new TERenderer();
        w = lastGame.w;
        world = new TETile[WIDTH][HEIGHT];
        return lastGame.seed;
    }

    /* runs the Menu operations, uses menu listen. */
    public Long runMenu() {
        MenuListen l = new MenuListen();
        Menu.drawMenu();
        while (l.listening) {
            l.menuListen();
        }
        callSequence = l.calls;
        return l.seed;
    }

    /**
     * runs program regardless of input type
     */
    public void runProgram(long seed) {
        Listener l = new Listener(w);

        if (l.checkWinLose() == false) {
            displayEndScreen();
        } else {
            l.giveTer(ter);
            l.giveWorld(world);
            render(seed, l);
            while (l.listening) {
                if (l.listen()) {
                    render(seed, l);
                }
            }
            callSequence += l.calls;
            if (l.save) {
                quitSave = true;
            }
        }
    }

    /** display win lose screen */
    public void displayEndScreen() {
        if (w.player.dead()) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(w.WORLD_WIDTH / 2, w.WORLD_HEIGHT / 2, "YOU HAVE LOST");
        } else {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(w.WORLD_WIDTH / 2, w.WORLD_HEIGHT / 2, "YOU HAVE WON!");
            w.won = true;
        }
    }

    /**
     * Initializes engine's instance variables. Returns the world
     **/
    public TETile[][] initialize(long seed) {
        ter = new TERenderer();
        world = new TETile[WIDTH][HEIGHT];
        w = new World(seed, WIDTH, HEIGHT);
        return new TETile[WIDTH][HEIGHT];
    }

    /**
     * renders the world
     **/
    public void render(long seed, Listener l) {

        StdDraw.show();

        if (!displayDead()) {
            renderSpace(seed); //renders walls and hallways
            renderCharacters(); //renders the player locations and objects
            ter.renderFrame(world);
            if (l != null) {
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(10, w.WORLD_HEIGHT + .7, l.hud + " // " + "Carrot Count: " + w.player.lives);
                StdDraw.show();
            }
        }
    }

    /**
     * Sets the rooms and the hallways TETiles
     **/
    public void renderSpace(long seed) {
        TETile randomTheme = randomizeTheme(seed);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = randomTheme;
            }
        }
        for (Location l : w.getWalkableLocations()) {
            world[l.getX()][l.getY()] = Tileset.FLOOR;
        }
        for (Location l : w.getWalls()) {
            world[l.getX()][l.getY()] = Tileset.WALL;
        }
        if (w.encounter) {
            for (Location l: w.traps) {
                world[l.getX()][l.getY()] = Tileset.TREE;
            }
            world[w.escape.getX()][w.escape.getY()] = Tileset.LOCKED_DOOR;
        } else {
            world[w.winningDoor.getX()][w.winningDoor.getY()] = Tileset.UNLOCKED_DOOR;
        }
    }
    /** returns true if the game is over and displays whether or not you have won. */
    public Boolean displayDead() {
        if (w.player.dead()) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(w.WORLD_WIDTH/2, w.WORLD_HEIGHT /2, "YOU HAVE LOST");
            StdDraw.show();
            return true;
        }
        if ((w.player.getLocation().equals(w.winningDoor) && !w.encounter && w.player.lives == 10) || w.won) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(w.WORLD_WIDTH / 2, w.WORLD_HEIGHT / 2, "YOU HAVE WON!");
            StdDraw.show();
            w.won = true;
            return true;
        }
        return false;
    }


    /**
     * Displays player and objects as TETiles if there is no encounter occurring
     **/
    public void renderCharacters() {

        if (!w.encounter) {
            for (Location l : w.carrotLocations.keySet()) {
                Collectable tempCarrot = w.carrotLocations.get(l);
                if (!w.player.getCollection().contains(tempCarrot)) {
                    world[l.getX()][l.getY()] = Tileset.CARROT;
                }
            }
        }
        Location avatar = w.player.getLocation();
        world[avatar.getX()][avatar.getY()] = Tileset.AVATAR;

        Location duck = w.duck.getLocation();
        world[duck.getX()][duck.getY()] = Tileset.DUCK;

    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quit save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] interactWithInputString(String input) { //need to make this work with seed
        ArrayList<Character> sequence = stringToCalls(input);
        LoadStringState loadWorld = new LoadStringState(sequence, WIDTH, HEIGHT);
        w = loadWorld.w;
        Long seed = loadWorld.seed;
        TETile[][] finalWorldFrame = run(seed, loadWorld.w);
        return finalWorldFrame;
    }

    // add quit save protocol

    /**
     * helper method that runs the program. Lowkey if there are bugs it might be here
     */
    public TETile[][] run(long seed, World currWorld) {

        // initialize tiles
        TETile[][] myWorld = new TETile[currWorld.WORLD_WIDTH][currWorld.WORLD_HEIGHT];
        for (int x = 0; x < currWorld.WORLD_WIDTH; x += 1) {
            for (int y = 0; y < w.WORLD_HEIGHT; y += 1) {
                myWorld[x][y] = Tileset.NOTHING;
            }
        }
        for (Location l : currWorld.getWalkableLocations()) {
            myWorld[l.getX()][l.getY()] = Tileset.FLOOR;
        }
        for (Location l : currWorld.getWalls()) {
            myWorld[l.getX()][l.getY()] = Tileset.WALL;
        }
        Location avatar = currWorld.player.getLocation();
        myWorld[avatar.getX()][avatar.getY()] = Tileset.AVATAR;

        Location duck = currWorld.duck.getLocation();
        myWorld[duck.getX()][duck.getY()] = Tileset.SAND;

        for (Location l : currWorld.carrotLocations.keySet()) {
            Collectable tempCarrot = currWorld.carrotLocations.get(l);
            if (!currWorld.player.getCollection().contains(tempCarrot)) {
                myWorld[l.getX()][l.getY()] = Tileset.CARROT;
            }
        }
        return myWorld;
    }

    /* PRIVATE HELPER METHODS */

    /**
     * picks a random theme for tettile
     */
    private TETile randomizeTheme(long seed) {

        Random r = new Random(seed);
        int theme = r.nextInt(3);
        if (theme == 0) {
            return Tileset.GRASSY;
        }
        if (theme == 1) {
            return Tileset.STONE;
        }
        if (theme == 2) {
            return Tileset.BRICK;
        } else {
            return Tileset.NOTHING;
        }
    }

    /* @source: https://stackoverflow.com/questions/15331637/convert
    -string-to-arraylist-character-in-java*/
    private ArrayList<Character> stringToCalls(String inputString) {
        ArrayList<Character> callChars = new ArrayList<>();
        for (char c : inputString.toCharArray()) {
            callChars.add(c);
        }
        return callChars;
    }

    private String cleanCharSeq(ArrayList<Character> returnList) {
        List<Character> validInts = List.of('0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', 'w', 'W', 'a', 'A', 's', 'S', 'd', 'D');
        String cleaned = "";
        for (Character c : returnList) {
            if (c == 'N' || c == 'n') {
                cleaned += c;
            }
            if (validInts.contains(c)) {
                cleaned += c;
            }
        }
        return cleaned;
    }

    private void saveWorldToText() {
        MenuListen temp = new MenuListen();
        if (temp.savedWorldExists()) {
            new DeleteOldCallFile();
        }
        new WriteCallFile(callSequence);
    }

    private ArrayList<Character> retrieveCharacters() {
        ReadCallsToString readFile = new ReadCallsToString();
        ArrayList<Character> returnList = readFile.retrievedChars;
        return returnList;
    }

    private void quitProgram() {
        Menu.drawQuitScreen();
        Stopwatch wait = new Stopwatch();
        while (wait.elapsedTime() < 3) {
            Menu.drawQuitScreen();
        }
        System.exit(0);
    }
}
