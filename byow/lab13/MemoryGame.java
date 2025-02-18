package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/** @source https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html */

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Please enter a seed");
        }

        //long seed = Long.parseLong(args[0]);
        //MemoryGame game = new MemoryGame(40, 40, seed);

        MemoryGame game = new MemoryGame(40, 40, 20);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random(seed);
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {

        String rtn = "";
        for (int i = 0; i < n; i++) {
            int idx = rand.nextInt(CHARACTERS.length-1);
            char c = CHARACTERS[idx];
            rtn += c;
        }
        return rtn;
    }

    public void drawFrame(String s) {

        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Comic Sans", Font.BOLD, 30));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show(1000);
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {

        StdDraw.clear(Color.BLACK);
        String s = "";
        //for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(0);
            s += c;

            drawFrame(s);
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);
        //}
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String rtn = "";
        for (int i = 0; i < n; i++) {
            while(StdDraw.hasNextKeyTyped()) {
                rtn += StdDraw.nextKeyTyped();
            }
        }
        return rtn;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Engine loop

        boolean lost = false;
        int round = 1;
        while (!lost) {
            drawFrame("Round: " + round);
            String r = generateRandomString(round);
            flashSequence(r);
            System.out.print("go");
            String user = solicitNCharsInput(round);
            if (user.equals(r)) {
                round += 1;
            } else {
                lost = true;
            }

        }

    }

}
