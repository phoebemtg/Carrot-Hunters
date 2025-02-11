package byow.Core;


import java.io.File;

/* @source: https://www.w3schools.com/java/java_files_create.asp
 *  @source: https://stackoverflow.com/questions/14209085/how-to-define-a-relative-path-in-java
 *  @source: https://www.tutorialspoint.com/get-the-current-working-directory-in-java */
public class CreateCallFile {
    /**
     public void CreateCallFile() {
     try {
     String localDir = System.getProperty("user.dir");
     File callSequence = new File(localDir + "/callSequence.txt");
     //String repoDir = this.getClass().getClassLoader().
     getResource("callSequence.txt").toString();
     //File callSequence = new File(repoDir);
     if (callSequence.createNewFile()) {
     System.out.println("Successfully created new file: callSequence.txt");
     } else {
     System.out.println("file already exists!");
     }
     } catch (IOException e) {
     System.out.println("error!");
     e.printStackTrace();
     } catch (AccessControlException a) {
     System.out.println("READ CALLS TO STRING ERROR");
     }
     }
     */

}
