/** 
 * FindTrend.java
 * Phuong Nguyen Ngoc 
 * CS231
* Project: Word Trends
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.io.FileWriter;

public class FindTrend {
    private WordCounter counter;

    public FindTrend() {
        this.counter = new WordCounter(311);
    }

    
    //read the word count file to build the map in WordCounter object
    public void read(String filename) throws IOException {
        counter.readWordCountFile(filename);        
    }

    //return the frequency of the word
    public double getTrend(String str) {
        return counter.getFrequency(str);
    }

    public static void main(String[] args)throws IOException {
        for (int i = Integer.parseInt(args[1]); i <= Integer.parseInt(args[2]); i ++) {
            String filename = args[0] + i + ".txt";
            FindTrend trend = new FindTrend();
            trend.read(filename);
            System.out.println("Year: " + i);
            for (int x = 3; x < args.length; x ++) {
                double freq = trend.getTrend(args[x]);
                System.out.println(args[x] + ": " + freq);
            }
            System.out.println("\n");
            
        }
        
        
        
    }
}