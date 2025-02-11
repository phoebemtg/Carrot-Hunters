package byow.Core;

import java.io.File;
import java.security.AccessControlException;

/* @source: https://www.w3schools.com/java/java_files_delete.asp */
public class DeleteOldCallFile {

 public void DeleteOldCallFile() {
     try {
        String localDir = System.getProperty("user.dir");
        File callSequence = new File(localDir + "/callSequence.txt");
        if (callSequence.delete()) {
            System.out.println("Deleted the file: " + callSequence.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
     } catch (AccessControlException a) {
            System.out.println("READ CALLS TO STRING ERROR");
     }
     }

}