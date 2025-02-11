package byow.Core;

import java.util.ArrayList;
import java.util.List;


public class LoadStringState {
    /* handles string inputs up to :Q and records the call sequence
     * should also handle saving and loading */
    World w;
    InputStringListener applyMovement;
    Long seed;
    String cleanedCalls;
    // cases to account for:
    // input is N # S movement Q (no save)
    // input is N # S movement :Q (save)
    // input is L movement :Q (needs to add old string to new string and save)
    // input is L movement Q (doesn't need to save)

    public LoadStringState(ArrayList<Character> callSequence, int width, int height) {
        if (callSequence.get(0) == 'L' || callSequence.get(0) == 'l') {
            callSequence = mergeWorlds(callSequence); // removes L and Q
        }
        seed = getSeed(callSequence);
        w = createWorld(width, height, seed);
        applyMovement = new InputStringListener(w, callSequence);
        cleanedCalls = charListToString(callSequence);
        while (applyMovement.listening) {
            applyMovement.listen();
        }
        if (applyMovement.save) {
            saveWorldToText(cleanedCalls);
        }
    }

    private World createWorld(int width, int height, Long seedWorld) {
        World newWorld = new World(seedWorld, width, height);
        return newWorld;
    }

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

    private ArrayList<Character> mergeWorlds(ArrayList<Character> newWorld) {
        newWorld.remove(0);
        ArrayList oldWorld = getOldWorld();
        ArrayList<Character> merged = new ArrayList<>(oldWorld);
        merged.addAll(newWorld);
        return merged;
    }

    private ArrayList<Character> getOldWorld() { //no Q!
        ArrayList<Character> readList = new ReadCallsToString().retrievedChars;
        ArrayList<Character> cleanedWorld = cleanCharSeq(readList);
        return cleanedWorld;
    }

    private void saveWorldToText(String characterInputs) {
        MenuListen temp = new MenuListen();
        if (temp.savedWorldExists()) {
            new DeleteOldCallFile();
        }
        new WriteCallFile(characterInputs);
    }

    private String charListToString(List<Character> charArray) {
        String output = "";
        for (Character c : charArray) {
            output += c;
        }
        return output;
    }

    private ArrayList<Character> cleanCharSeq(ArrayList<Character> listToClean) {
        List<Character> validChars = List.of('0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', 'w', 'W', 'a', 'A', 's', 'S', 'd', 'D', 'N', 'n');
        ArrayList<Character> cleanedList = new ArrayList<>();
        for (Character c : listToClean) {
            if (validChars.contains(c)) {
                cleanedList.add(c);
            }
        }
        return cleanedList;
    }


    class InputStringListener extends Listener {
        /**
         * Class that listens to key movement to move player
         *
         * @param w
         */
        //save attribute
        ArrayList<Character> charSequence;
        int currentIndex;

        InputStringListener(World w, ArrayList<Character> charInputs) {
            super(w);
            charSequence = charInputs;
            currentIndex = 0;
        }

        @Override
        public char getKey() {
            if (charSequence.size() - 1 == currentIndex) {
                listening = false;
            }
            char currChar = charSequence.get(currentIndex);
            currentIndex++;
            return currChar;


        }
    }


}
