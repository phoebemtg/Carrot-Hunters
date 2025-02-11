package byow.Core;
import java.io.FileWriter;
import java.io.IOException;

/* @source: https://www.w3schools.com/java/java_files_create.asp */
public class WriteCallFile {

    public WriteCallFile(String args) {
        try {
            String localDir = System.getProperty("user.dir");
            FileWriter writeCalls = new FileWriter(localDir + "/callSequence.txt");
            System.out.println(writeCalls);
            writeCalls.write(args);
            writeCalls.close();
            System.out.println("save complete");
        } catch (IOException e) {
            System.out.println("error:");
            e.printStackTrace();
        }
    }
}
