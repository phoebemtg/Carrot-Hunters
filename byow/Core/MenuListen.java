package byow.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.util.List;
import java.net.URL;

import edu.princeton.cs.introcs.Stopwatch;
/* @source: https://beginnersbook.com/2013/12/how-to-convert-string-to-int-in-java/ */
public class MenuListen {
    Boolean listening;
    Boolean listeningToChoice;
    Boolean listeningToSeed;
    String stringSeed;
    Long seed;
    List<Character> validInts;
    String calls;


    public MenuListen() {
        /* a string recording all the character inputs to the game.
        * Does not ever get converted to an integer. used for saving/loading. */
        calls = "";
        /* MenuListen will have attribute listening = true until
        * the user decides to quit or quit/save the game. */
        listening = true;
        /* listening To Choice determines whether the user is navigating
        * the menu (N, L, Q, R, I, B). */
        listeningToChoice = true;
        /* listeningToSeed indicates whether the game is currently taking
        * in an input seed. Default to false until the user presses N. */
        listeningToSeed = false;
        /* a string recording all the input seed. */
        stringSeed = "";
        /* a list of valid seed values. */
        validInts = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    }


    /* @source: https://beginnersbook.com/2013/12/how-to-convert-string-to-int-in-java/ */

    /* MenuListen records the last key the user pressed, and
    * displays the screen corresponding to that key-action. */
    public void menuListen() {
        char choice = getKey();
        if (listeningToChoice) {
            displayChoice(choice);
        } else if (listeningToSeed) {
            manageSeeding(choice);
        }
    }

    /** @source Hug's Key code in the KeyboardInputSource Class */
    /**
     * returns the char value of the key pressed by user
     */
    private char getKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char rtn = StdDraw.nextKeyTyped();
                return rtn;
            }
        }
    }

    /* Display choice draws a relevant screen for the input choice character
    * for menu navigation, will change listeningToChoice/listeningToSeed when
    * going between the menu and the seed input screen. */
    private void displayChoice(char choice) {
        if (choice == 'N' || choice == 'n') {
            Menu.drawPromptSeed();
            listeningToChoice = false;
            listeningToSeed = true;
            return;
        } else if (choice == 'L' || choice == 'l') {
            if (this.savedWorldExists()) {
                calls = ""; // calls should be cleaned when the user chooses L so
                // that it doesn't appear on the recorded call string.
                listening = false;
                listeningToChoice = false;
                listeningToSeed = false;
                seed = Long.parseLong("-1");
            } else {
                Menu.invalidLoad();
                listeningToChoice = true;
                listeningToSeed = false;
            }
        } else if (choice == 'Q' || choice == 'q') {
            listening = false;
            Menu.drawQuitScreen();
            Stopwatch wait = new Stopwatch();
            while (wait.elapsedTime() < 3) {
                continue;
            }
            System.exit(0);
            return;
            /**
        } else if (choice == 'I' || choice == 'i') {
            Menu.carrotInspoScreen();
            listeningToChoice = true;
             */
        } else if (choice == 'B' || choice == 'b') {
            Menu.drawMenu();
            listeningToChoice = true;
        } else if (choice == 'R' || choice == 'r') {
            Menu.drawMenu();
            listeningToChoice = true;
        }
    }

    /* ManageSeeding takes in a character and updates the
    * screen displaying the seed input so far, as well as displaying
    * the screen telling a user when their input is invalid.
    * also initializes the game by setting listening to false when
    * the player hits s. */
    private void manageSeeding(char choice) {
        if (choice == 's' || choice == 'S') {
            if (stringSeed.equals("")) {
                listening = true;
                Menu.invalidSeed("Empty Seed");
                listeningToSeed = false;
                stringSeed = "";
                listeningToChoice = true;
            } else {
                calls =  "N" + stringSeed + "S";

                seed = Long.parseLong(stringSeed);
                listeningToSeed = false;
                listening = false;
            }
        } else {
            stringSeed += choice;
            if (!validInts.contains(choice)) {
                listening = true;
                Menu.invalidSeed(stringSeed);
                listeningToSeed = false;
                stringSeed = "";
                listeningToChoice = true;
            } else {
                Menu.drawPromptWSeed(stringSeed);
                listening = true;
                listeningToSeed = true;
            }
        }
    }

    public boolean savedWorldExists() {
        URL pathToSeq = this.getClass().getClassLoader().getResource("callSequence.txt");
        return pathToSeq != null;
    }

    public static void main(String[] args) {
        MenuListen test = new MenuListen();
        boolean testNames = test.savedWorldExists();
        System.out.println(testNames);

    }
}
