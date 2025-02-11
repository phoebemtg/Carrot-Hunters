package byow.Core;

import java.util.ArrayList;

/* Restore Game state should be called when a user inputs 'L' or 'l'
 * to the menu. It will take a list of string inputs, replay the game,
 * and then when the end of the string is reached, it will render the game image.
 * Should also delete the old file when it is done rendering. */

public class RestoreGameState {
    World w;
    RestorationListener modifyWorld;
    Long seed;

    /* the Restore Game State Constructor takes in a list of characters representing
    * the last game's calls, the world width, and the world height, sets the
    * seed, creates a new world, and then modifies the world using the restoration listener
    * class. */
    public RestoreGameState(ArrayList<Character> callSequence, int width, int height) {
        seed = getSeed(callSequence);
        w = createWorld(width, height, seed);
        modifyWorld = new RestorationListener(w, callSequence);
        while (modifyWorld.listening) {
            modifyWorld.listenSpecial();
        }
    }

    /* creates a world based on the seed given in Call Sequence.
     * should not destructively modify callSequence. */
    private World createWorld(int width, int height, long seedWorld) {
        World newWorld = new World(seedWorld, width, height);
        return newWorld;
    }

    /* gets the seed in the call sequence, and converts it into a long. */
    private Long getSeed(ArrayList<Character> callSequence) {
        int index = 0;
        while (callSequence.get(index) != 'n' && callSequence.get(index) != 'N') {
            index++;
        }
        index++;
        String currSeed = "";
        while (callSequence.get(index) != 's' && callSequence.get(index) != 'S') {
            currSeed += callSequence.get(index);
            index++;
        }
        return Long.parseLong(currSeed);
    }

    /* Restoration Listener class extends Listener class in order to perform
    * all the movement operations as done in the Listener class. */
    class RestorationListener extends Listener {
        /**
         * Class that listens to key movement to move player
         *
         * @param w
         */
        ArrayList<Character> callSequence;
        int currentIndex;

        RestorationListener(World w, ArrayList<Character> callSeq) {
            super(w);
            callSequence = callSeq;
            currentIndex = 0;
        }

        @Override
        public char getKey() {
            char currChar = callSequence.get(currentIndex);
            currentIndex++;
            return currChar;
        }

    }
}
