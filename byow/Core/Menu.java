package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;

public class Menu {

    /* Creates a list of carrot file names from the carrotFile folder */
    public static ArrayList<String> carrotListConstructor() {
        ArrayList<String> carrotPics = new ArrayList<>();
        String localDir = System.getProperty("user.dir");
        File[] files = new File(localDir + "/byow/Core/carrotFiles").listFiles();
        for (File image : files) {
            if (image.isFile()) {
                carrotPics.add(image.getName());
            }
        }
        return carrotPics;
    }

    /* selects a random carrot image name from the list of carrot images in the carrot
    * file folder */
    public static String selectCarrotImage() {
        ArrayList<String> carrotImages = carrotListConstructor();
        Random rand = new Random();
        int index = RandomUtils.uniform(rand, 0, carrotImages.size());
        String imageName = carrotImages.get(index);
        return System.getProperty("user.dir") + "/byow/Core/carrotFiles" + imageName;
    }

    /* displays the inputted image */
    public static void drawCarrotInspo(String imageName) {
        StdDraw.picture(0.5, 0.5, imageName, 1, 1);
    }
    /**
    /* Draws the whole screen with a carrot image */
    /**
    public static void carrotInspoScreen() {
        StdDraw.clear(StdDraw.BLACK);
        String name = selectCarrotImage();
        drawCarrotInspo(name);
        Font font = new Font("Sans Serif", Font.BOLD, 20);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(font);
        StdDraw.text(0.1, 0.1, "Back (B)");
    }
    /**
    /* Draws the initial menu screen */
    public static void drawMenu() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.ORANGE);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.8, "Carrot Hunter: The Quest");
        font = new Font("Sans Serif", Font.PLAIN, 15);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.4, "Load Game (L)");
        StdDraw.text(0.5, 0.3, "Quit (Q)");
        //"LStdDraw.text(0.5, 0.2, "Carrot Inspo (I)");
    }

    /* Draws the screen asking the user to input a seed, but does
    * not display any input seed number (only used for initial prompt) */
    public static void drawPromptSeed() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.ORANGE);
        Font font = new Font("Sans Serif", Font.PLAIN, 15);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.8, "Please Enter a Seed:");
        StdDraw.text(0.5, 0.2, "Press S to generate world");
    }

    /* Draws the screen asking the user to input a seed, but
    * takes in a string input displaying what the user input */
    public static void drawPromptWSeed(String seed) {
        drawPromptSeed();
        StdDraw.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        StdDraw.text(0.5, 0.7, seed);
        StdDraw.text(0.5, 0.2, "Press S to generate world");
    }

    /* Draws the screen displayed after the user decides to quit the game */
    public static void drawQuitScreen() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.ORANGE);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.4, "QUITTING GAME...");
    }

    /* Draws the screen telling the user they inputted an invalid screen */
    public static void invalidSeed(String invalidSeed) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.ORANGE);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.6, "INVALID SEED:");
        StdDraw.text(0.5, 0.5, invalidSeed);
        font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.4, "Press r to try again.");
    }

    public static void invalidLoad() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.ORANGE);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.6, "INVALID LOAD COMMAND:");
        StdDraw.text(0.5, 0.5, "NO SAVED GAME");
        font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.4, "Press r to try again.");
    }


    public static void main(String[] args) {
        invalidLoad();
    }
}


