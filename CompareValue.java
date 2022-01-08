import java.util.Comparator;
/** 
 * CompareValue.java
 * Phuong Nguyen Ngoc 
 * CS231
 * Project: Word Trends
*/

public class CompareValue implements Comparator<KeyValuePair<String,Integer>> {
    //compare the value as an integer of the key
    public int compare(KeyValuePair<String,Integer> a, KeyValuePair<String,Integer> b) {
        int aVal = a.getValue();
        int bVal = b.getValue();
        if ((aVal - bVal) < 0 ) {
            return -1;
        } else if ((aVal - bVal) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}