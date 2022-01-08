/** 
 * FindCommonWords.java
 * Phuong Nguyen Ngoc 
 * CS231
 * Project: Word Trends
*/

import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;


public class FindCommonWords {
    
    private PQHeap heap;
    private ArrayList<KeyValuePair<String,Integer>> alist;
    private NodeBasedHeap<KeyValuePair<String,Integer>> node;

    //constructor 
    public FindCommonWords() {
       
        this.heap = new PQHeap<KeyValuePair<String,Integer>>(new CompareValue());
        this.node = new NodeBasedHeap<KeyValuePair<String,Integer>>(new CompareValue());
        this.alist = new ArrayList<KeyValuePair<String,Integer>>();
        
    } 


    //read from a count file to construct the heap
    public void readWordCountFile( String filename ) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
            //read first line separately
            String[] firstLine = line.split("[^a-zA-Z0-9']");
            line = bufferReader.readLine();
            while (line != null ) { 
                String[] words = line.split("[^a-zA-Z0-9']");
                
                heap.add(new KeyValuePair(words[0],Integer.parseInt(words[2]) ));
                // node.add(new KeyValuePair(words[0],Integer.parseInt(words[2]) ));
                
                line = bufferReader.readLine();
            }
            bufferReader.close();
            return;    
        }

        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file " + filename );
        }
        catch(IOException ex) {
            System.out.println("Error reading file " + filename);
        }
        return;
        
    }

    //print out n most frequent words
    public void getMostFrequent(int time) {
        
        for (int i = 0; i < time; i ++) {
            System.out.println(this.heap.remove() + " ");
            // System.out.println(this.node.remove() + " ");
        }
        System.out.println("\n");
    }

    //read from a count file to and put words to the ArrayList
    public void readInArrayList( String filename ) {
        
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
            //read first line separately
            String[] firstLine = line.split("[^a-zA-Z0-9']");
            line = bufferReader.readLine();
            while (line != null ) { 
                String[] words = line.split("[^a-zA-Z0-9']");
                alist.add(new KeyValuePair(words[0],Integer.parseInt(words[2]) ));
                line = bufferReader.readLine();
            }
            bufferReader.close();
            mergeSort(alist);
            return;    
        }

        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file " + filename );
        }
        catch(IOException ex) {
            System.out.println("Error reading file " + filename);
        }
        return;
        
    }

    //divide the ArrayList in halves until it cannot be divided anymore, then merge them to get a sorted array
    public void mergeSort(ArrayList<KeyValuePair<String,Integer>> a) {
        int n = a.size();
        if (n<2) return;
        int mid = n/2;
        ArrayList<KeyValuePair<String,Integer>> left = new ArrayList<KeyValuePair<String,Integer>>();
        ArrayList<KeyValuePair<String,Integer>> right = new ArrayList<KeyValuePair<String,Integer>>();
            for (int i = 0; i <= mid - 1; i ++) {
                left.add(a.get(i));
            }
            for (int i = mid; i <= n - 1; i ++ ) {
                right.add(a.get(i));
            }
        mergeSort(left);
        mergeSort(right);
        merge(left,right,a);
    }
    //merge the subsets into a parent set to sort it
    public void merge(ArrayList<KeyValuePair<String,Integer>> left, ArrayList<KeyValuePair<String,Integer>> right, ArrayList<KeyValuePair<String,Integer>> a) {
        CompareValue comp = new CompareValue();
        int numLeft = left.size();
        int numRight = right.size();
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < numLeft && j < numRight) {
            int c = comp.compare(left.get(i), right.get(j));
            if (c >= 0) {
               a.set(k, left.get(i));
               i++;
            } else {
                a.set(k, right.get(j));
                j++;
            } 
            k++;
        }
        while (i < numLeft) {
            a.set(k, left.get(i));
            i++;
            k++;
        }

        while (j < numRight) {
            a.set(k, right.get(j));
            j++;
            k++;
        }

    }

    

    //print out n most frequent words using ArrayList
    public void usingArrayList(int times) {
        for (int i = 0; i < times; i ++) {
            System.out.println(alist.remove(0));
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < args.length ; i ++) {
            long timeStart = System.currentTimeMillis();
            FindCommonWords common = new FindCommonWords();

            common.readWordCountFile(args[i]);
            System.out.println("Most " + args[0] + " frequent words for file " + args[i] + ":" );
            common.getMostFrequent(Integer.parseInt(args[0]));

            // common.readInArrayList(args[i]);
            // System.out.println("Most " + args[0] + " frequent words for file " + args[i] + ":" );
            // common.usingArrayList(Integer.parseInt(args[0]));
            long timeEnd = System.currentTimeMillis();
            long time = timeEnd - timeStart;
            System.out.println("processing time for file " + i + ": " + time);
        }

    }


}