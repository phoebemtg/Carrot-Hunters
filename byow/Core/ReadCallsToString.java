package byow.Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.security.AccessControlException;
import java.util.ArrayList;

/* @source: https://www.w3schools.com/java/java_files_read.asp */

public class ReadCallsToString {
    ArrayList<Character> retrievedChars;

    public ReadCallsToString() {
        retrievedChars = readCharacters();
    }

    public ArrayList<Character> readCharacters() {
        ArrayList<Character> charCalls = new ArrayList<>();
        try {
            String filePath = System.getProperty("user.dir");
            File callSeq = new File(filePath + "/callSequence.txt");
            FileReader readSequence = new FileReader(callSeq);
            int justRead = readSequence.read();
            while (justRead != -1) {
                //char currChar = Character.forDigit(justRead, 16);
                charCalls.add((char) justRead);
                justRead = readSequence.read();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        } catch (AccessControlException a) {
            System.out.println("READ CALLS TO STRING ERROR");
        }
        return charCalls;
    }
}



