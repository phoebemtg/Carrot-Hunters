package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.*;
import java.util.List;
/**
 * @souce Hugs demo code in KeyboardInput Source
 *
 * @souce https://beginnersbook.com/2019/04/java-char-to-string-conversion/
 */

/**
 * Class that Listens to key movements
 */
public class Listener {
    World w;
    VerifyMovement vmPlayer;
    Boolean listening;
    String calls;
    char lastTyped;
    boolean save;
    List<Integer> mouseX;
    List<Integer> mouseY;
    TERenderer ter;
    String hud;
    TETile[][] tetWorld;
    int carrotCount;

    /**
     * Class that listens to key movement to move player
     **/
    public Listener(World w) {
        carrotCount = 0;
        tetWorld = null;
        this.hud = "";
        this.w = w;
        this.vmPlayer = new VerifyMovement(w, w.player);
        listening = true;
        calls = "";
        save = false;
        mouseX = new Stack();
        mouseY = new Stack();
    }

    /**
     * listens to key movements to check for changing player position
     **/
    public boolean listen() {
        if (w.encounter) {
            vmPlayer.reset();
        }
        char typed = getKey(); //repr the char that is pressed by user
        checkMovement(typed); //checks if key pressed is WASD
        lastTyped = typed;
        return listening;
    }

    /*called when reseting world */
    public boolean listenSpecial() {
        if (w.encounter) {
            vmPlayer.reset();
        }
        char typed = getKey(); //repr the char that is pressed by user
        checkSpecial(typed); //checks if key pressed is WASD
        lastTyped = typed;
        return listening;
    }

/** called only when reseting world */
    public void checkSpecial(char typed) {

        if (typed == 'W' || typed == 'w') {
            calls += typed;
            if (vmPlayer.move(0, 1)) {

                w.player.moveUp();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();
            }
        } else if (typed == 'S' || typed == 's') {
            calls += typed;
            if (vmPlayer.move(0, -1)) {

                w.player.moveDown();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();
            }
        } else if (typed == 'A' || typed == 'a') {
            calls += typed;
            if (vmPlayer.move(-1, 0)) {

                w.player.moveLeft();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();

            }
        } else if (typed == 'D' || typed == 'd') {
            calls += typed;

            if (vmPlayer.move(1, 0)) {
                w.player.moveRight();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();
            }
        } else if ((typed == 'Q' || typed == 'q') && lastTyped == ':') {
            calls += typed;
            listening = false;
            save = true;

            return;
        } else if (typed == 'Q' || typed == 'q') {
            calls += typed;
            listening = false;
            return;

        }
        checkWinLose();
    }
    /**
     * checks if the key pressed is for character movement
     */
    public void checkMovement(char typed) {

        if (typed == 'W' || typed == 'w') {
            calls += typed;
            if (vmPlayer.move(0, 1)) {

                w.player.moveUp();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();
            }
        } else if (typed == 'S' || typed == 's') {
            calls += typed;
            if (vmPlayer.move(0, -1)) {

                w.player.moveDown();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();
            }
        } else if (typed == 'A' || typed == 'a') {
            calls += typed;
            if (vmPlayer.move(-1, 0)) {

                w.player.moveLeft();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();

            }
        } else if (typed == 'D' || typed == 'd') {
            calls += typed;

            if (vmPlayer.move(1, 0)) {
                w.player.moveRight();
                w.checkCarrots();
                w.moveDuck();
                checkEncounter();
            }
        } else if ((typed == 'Q' || typed == 'q') && lastTyped == ':') {
            calls += typed;
            listening = false;
            save = true;

            return;
         } else if ((typed == 'Q' || typed == 'q') && !w.running) {
            calls += typed;
            listening = false;
            return;

        }
        checkWinLose();
    }

    public boolean checkWinLose() {
        if (w.player.dead()) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(w.WORLD_WIDTH / 2, w.WORLD_HEIGHT / 2, "YOU HAVE LOST");
            return false;
        }
        if ((w.player.getLocation().equals(w.winningDoor) && !w.encounter && w.player.lives == 10) || w.won) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(w.WORLD_WIDTH / 2, w.WORLD_HEIGHT / 2, "YOU HAVE WON!");
            w.won = true;
            return false;
        }
        return true;
    }

    /** @source Hug's Key code in the KeyboardInputSource Class */
    /**
     * returns the char value of the key pressed by user
     */
    public char getKey() {
        while (true) {
            mouseX.add((int) StdDraw.mouseX());
            mouseY.add((int) StdDraw.mouseY());



            if (StdDraw.hasNextKeyTyped()) {
                char rtn = StdDraw.nextKeyTyped();
                return rtn;
            }
            checkMouses();
        }
    }
    /** allows the listener to store the tilerender locally */
    public void giveTer(TERenderer t) {
        ter = t;
    }
    /** allows the listener to store the TETile world locally */
    public void giveWorld(TETile[][] wTET) {
        this.tetWorld = wTET;
    }

    /** deals with mouse movements*/
    public void checkMouses() {
        char currHud = 'a';
        String tempHud = "";
        String message = "";
        int x = mouseX.get(0);
        int y = mouseY.get(0);
        mouseX.remove(0);
        mouseY.remove(0);
        if (tetWorld != null) {
            if (x < w.WORLD_WIDTH && y < w.WORLD_HEIGHT) {
                currHud = tetWorld[x][y].character();
                if (currHud == '·') {
                    tempHud = "Floor";
                    message = "Players can walk here!";
                } else if (currHud == '#') {
                    tempHud = "Wall";
                    message = "Players cannot walk here!";
                } else if (currHud == 'd') {
                    tempHud = "Duck";
                    message = "The Duck is Evil, do not collide with the duck!";
                } else if (currHud == '@') {
                    tempHud = "Avatar";
                    message = "This is me! Let's collect carrots!";
                } else if (currHud == 'c') {
                    tempHud = "Carrot";
                    message = "I am a carrot, come collect me!";
                } else if (currHud == '♠') {
                    tempHud = "Trap";
                    message = "This is a trap, if you step here, you will lose a life!";
                } else if (currHud == '▢'){
                    tempHud = "Exit";
                    message = "This is the exit.  To win the game, you must finish here with 10 carrots";
                } else if (currHud == '█') {
                    tempHud = "Exit";
                    message = "Walk through this door to exit the battlefield!";
                } else {
                    tempHud = "Outside Space";
                    message = "This is the cold outdoors";
                }
                if (tempHud != hud || w.player.lives != carrotCount) {
                    carrotCount = w.player.lives;
                    if (checkWinLose()) {
                        hud = tempHud;
                        StdDraw.clear();
                        ter.renderFrame(tetWorld);
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.text(10, w.WORLD_HEIGHT + .7, hud + " // Carrot Count: " + carrotCount);
                        StdDraw.text(40, w.WORLD_HEIGHT + .7, message);
                    }
                    StdDraw.show();

                }
            }
        }
    }

    /**
     * checks if players collide. If they do, initiate the encounter
     **/
    public void checkEncounter() {
        if (w.player.getLocation().equals(w.duck.getLocation())) {
            w.startEncounter();
        }
        if (w.encounter) {
            if (w.player.getLocation().equals(w.escape)) {
                w.encounter = false;
                w.reset();
                vmPlayer.reset();
            }
            for (Location curr : w.traps) {
                if (w.player.getLocation().equals(curr)) {
                    w.player.loseALife();

                }
            }
        }
    }

    /**
     * Class that verifies if a Beings state is valid in a given world.
     */
    class VerifyMovement {
        World w;
        Being b;
        HashSet<Location> valid;

        /**
         * Constructor: constructs a list of valid locations being can be in
         **/
        VerifyMovement(World w, Being b) {
            this.w = w;
            this.b = b;
            valid = new HashSet<>();
            for (Location l : w.getWalkableLocations()) {
                valid.add(l);
            }
        }

        /**
         * returns the Beings current location
         */
        private Location getCurr() {
            return b.getLocation();
        }

        /**
         * Checks if the Being can move increment their x and y location by int x, y
         * Returns true if movement is valid
         **/
        public boolean move(int x, int y) {
            Location newL = new Location(getCurr().getX() + x, getCurr().getY() + y);
            return valid.contains(newL);
        }

        /**
         * reset to new valid locations
         **/
        public void reset() {
            valid = new HashSet<>();
            for (Location l : w.getWalkableLocations()) {
                valid.add(l);
            }
        }
    }

}
