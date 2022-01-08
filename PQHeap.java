/** 
 * PQHeap.java
 * Phuong Nguyen Ngoc 
 * CS231
* Project: Word Trends
*/

import java.util.Comparator;
import java.lang.Math;

public class PQHeap<T> {
    private Object[] heap;
    private int numItems;
    private Comparator<T> comp;

    //constructor
    public PQHeap(Comparator<T> comp) {
        this.heap = new Object[32];
        this.numItems = 0;
        this.comp = comp;
    }

    //return number of items in the heap
    public int size() {
        return numItems;
    }

    //add items to the heap
    public void add(T obj) {
        ensureCapacity();

        //add the KeyValuePair to the next free space in the array
        heap[numItems] = obj;
        numItems++;
        //percolate up to find the suitable position to ensure Heap structure
        percolateUp();
    }

    //compare item to its parent and restructure the array
    private void percolateUp() {
        //the index for the last element in the array
        int outOfPlaceInd = numItems - 1;
    
        // swap with parents until we reach the root or the parent is larger than the current value.
        while (outOfPlaceInd > 0) {
            int parentInd = (outOfPlaceInd - 1)/2;
            int compare = this.comp.compare((T)(heap[outOfPlaceInd]),((T) (heap[parentInd])));
          //if the current value is larger than its parent, swap
          if (compare > 0) {
            swap(outOfPlaceInd, parentInd);
            // update the curr index to the parent's
            outOfPlaceInd = parentInd;
          }
          //else, the current element is in the right position, return
          else {
            return;
          }
        }
      }

    public void swap(int i, int j) {
        T copy = (T)(heap[i]);
        heap[i] = heap[j];
        heap[j] = copy;
    }

    public T remove() {
        if (numItems == 0) {
            // throw new IllegalStateException("Trying to remove from an empty PQ!");
            return null;
        }
          
        T p = (T)(heap[0]);
          // replace the root with the last element on the array
          numItems--;
          heap[0] = heap[numItems];
          
          //percolate down to ensure the Heap structure
          percolateDown();
          
          return p;
    }

    private void percolateDown() {    
      int outOfPlaceInd = 0; // parent
      int leftInd = 2*outOfPlaceInd + 1; // left child
      int rightInd = 2*outOfPlaceInd + 2; // right child

      //swap until the outOfPlaceInd value is larger than its larger child's value
      while (leftInd <= numItems - 1) {
        int biggerChildInd = leftInd;
        // if ((rightInd < numItems) && (((KeyValuePair<K,V>)(heap[rightInd])).compareTo((KeyValuePair<K,V>)(heap[leftInd])) > 0)) {
        if ((rightInd < numItems) && (this.comp.compare((T)(heap[rightInd]), (T)(heap[leftInd])) >0)) {
          biggerChildInd = rightInd;
        }
        int compare = this.comp.compare((T)(heap[outOfPlaceInd]), (T)(heap[biggerChildInd]));
        //if the current value is smaller than its larger child's value, swap and update the indices
        if (compare < 0)
        {
          swap(outOfPlaceInd, biggerChildInd);

          outOfPlaceInd = biggerChildInd;
          leftInd = 2*outOfPlaceInd + 1;
          rightInd = 2*outOfPlaceInd + 2;
        }
        //else, the current element is in the right place
        else
        {
          return;
        }
      }
    }

    //double array's size when necessary
    public void ensureCapacity() {
        if (numItems < heap.length/2) {
          return;
        }
        Object[] newHeap = new Object[2*heap.length];
        for (int i = 0; i < heap.length; i++) {
          newHeap[i] = heap[i];
        }   
        heap = newHeap;
    
      }

      //toString method prints out heap layer by layer
      public String toString() {
        String returnStr = "";
    
        int level = 0;
        int leftn = numItems;
        
        while (leftn > 0) {
          int count = 1;
          double pow = Math.pow(2.0, level);
          while (count <= pow )  {
            if (leftn == 0) 
              break;
            returnStr += heap[numItems-leftn] + " ";
            count++;
            leftn--;
          }
    
          if (leftn != 0) 
          returnStr += "\n";
          level++;
        }
        returnStr += "\n----------------\n";
    
        return returnStr;
      }

      public static void main(String[] args) {
        PQHeap<KeyValuePair<String,Integer>> pq = new PQHeap<KeyValuePair<String,Integer>>(new CompareValue());
        pq.add(new KeyValuePair<String,Integer>("Scott", 6));
        pq.add(new KeyValuePair<String,Integer>("Bob", 1));
        pq.add(new KeyValuePair<String,Integer>("Jone", 3));
        pq.add(new KeyValuePair<String,Integer>("Susan", 4));
        pq.add(new KeyValuePair<String,Integer>("Nate", 7));
        pq.add(new KeyValuePair<String,Integer>("Eddy", 2));
        pq.add(new KeyValuePair<String,Integer>("Doug", 0));
        System.out.println(pq);

        System.out.println("Removing Nate: " + pq.remove());
        System.out.println(pq);
        System.out.println("Removing Scot: " + pq.remove());
        System.out.println(pq);
        System.out.println("Removing Susan: " + pq.remove());
        System.out.println(pq);
        System.out.println("Removing Jone: " + pq.remove());
        System.out.println(pq);
        System.out.println("Removing Eddy: " + pq.remove());
        System.out.println(pq);
        System.out.println("Removing Bob: " + pq.remove());
        System.out.println(pq);
        System.out.println("Removing Dough: " + pq.remove());
        System.out.println("Size should be 0: " + pq.size());
      }
}