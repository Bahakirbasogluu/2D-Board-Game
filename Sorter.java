import java.util.Comparator;
public class Sorter implements Comparator<Characters>{
    public int compare(Characters o1,Characters o2){
        return o1.getName().compareTo(o2.getName());
    }
}