package byow.Core;

/**
public class TestCreateWriteRead {

    static List<Character> expected = List.of ('A', 'b', 'C', 'D', 'e', 'F', 'g', '1', '2', '3');

    public static void main(String[] args) {

        ReadCallsToString readChars = new ReadCallsToString();
        ArrayList<Character> returnList = readChars.retrievedChars;
        boolean works = expected.equals(returnList);
        if (works) {
            System.out.println("works!");
        } else {
            System.out.println("fail");
        }
        System.out.println(System.getProperty("user.dir"));
        try {
            URL url = new URL("proj3");
            System.out.println(url);
        } catch (MalformedURLException m){
            System.out.print("error");
        }
    }

}
 */
