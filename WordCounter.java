/** 
 * WordCounter.java
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
import java.util.ArrayList;
import java.util.Collections;


public class WordCounter {
    private MapSet<String,Integer> map;
    private int total;
    private boolean chooseMap;

    public WordCounter(int size) {
        if (size == 0) {
            this.map = new BSTMap<String, Integer>(new StringAscending());
            this.chooseMap = true;
        }
        else {
            this.map = new Hashmap<String, Integer>(size);
            this.chooseMap = false;
        }
        this.total = 0;
        
    } 


    //generates the word counts from a file of words
    public void analyze(String filename) {
        map.clear();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
    
            while (line != null) {
                
                String[] words = line.split("[^a-zA-Z0-9']");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim().toLowerCase();
                    if (word.length() != 0) {
                        Integer has = map.get(word);
                        if (has != null) {
                            map.put(word, has + 1);
                        } else {
                            map.put(word, 1);
                        }
                        this.total ++;
                    }
                   
                }
    
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

    //returns the total word count
    public int getTotalWordCount() {
        return total;
    }


    //returns the number of unique words
    public int getUniqueWordCount() {
        return map.size();
    }

    //returns the frequency value associated with this word
    public int getCount(String word) {
        if (map.get(word) == null) {
            return 0;
        }
        return map.get(word); 
    }

    
    public MapSet<String,Integer> getMap() {
        return this.map;
    }

    //returns the value associated with this word divided by the total word count. 
    public double getFrequency( String word ) {
        double frequency = ((double)this.getCount(word)) / this.getTotalWordCount();
        return frequency;
    }

    //writes the contents of the BSTMap to a word count file.
    public void writeWordCounterFile(String filename) throws IOException {
        String report = "totalWordCount: " + this.total + "\n";
        ArrayList<KeyValuePair<String,Integer>> set = map.entrySet();        
        FileWriter writeCount = new FileWriter(filename);
        writeCount.write(report);
        for (KeyValuePair<String,Integer> kv: set) {
            writeCount.write(kv + "\n");
        }
        writeCount.close();
    }

   


    //reads the contents of a word count file and reconstructs the fields of the WordCount object, including the BSTMap
    public void readWordCountFile( String filename ) {
        map.clear();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
            //read first line separately
            String[] firstLine = line.split("[^a-zA-Z0-9']");
            this.total = Integer.parseInt(firstLine[2]);
            line = bufferReader.readLine();
            while (line != null ) { 
                String[] words = line.split("[^a-zA-Z0-9']");
                
                map.put(words[0], Integer.parseInt(words[2]));
                
                
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


    
    public static void main(String[] args) throws IOException {
        //enable taking in multiple files in the command line and analyze them
        for (int i = 0; i < args.length; i ++) {long timeStart = System.currentTimeMillis();
            WordCounter test = new WordCounter(311);
            test.analyze(args[i]);
            int x = i + 2008;
            String fileName = "counts_reddit_comments_" + x + ".txt";
            test.writeWordCounterFile(fileName);
            test.readWordCountFile(fileName);
            // System.out.println(test.getMap().entrySet());
            long timeEnd = System.currentTimeMillis();
            long time = timeEnd - timeStart;
            System.out.println("processing time for file " + x + ": " + time);
            System.out.println("snapchat " + test.getCount("snapchat"));
            // test.map.getMax(20);    
            
        }

      


    }  

       
    

}