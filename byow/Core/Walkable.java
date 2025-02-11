package byow.Core;
import java.util.List;
/** Interface represents all objects that are can be walked through**/
public interface Walkable {
    void create();
    List<Location> getOccupied();
    List<Location> getEdges();
}
