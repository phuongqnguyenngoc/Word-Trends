/** 
 * StringAscending.java
 * Phuong Nguyen Ngoc 
 * CS231
 * Project: Word Trends
*/

import java.util.Comparator;


public class StringAscending implements Comparator<String>{
    //return a number indicates the result of the alphabetical comparison between a and b
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
}