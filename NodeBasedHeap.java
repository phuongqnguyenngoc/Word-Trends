/** 
 * NodeBasedHeap.java
 * Phuong Nguyen Ngoc 
 * CS231
* Project: Word Trends
*/

import java.util.Comparator;
import java.lang.Math;
import java.text.NumberFormat.Style;


public class NodeBasedHeap<T> {
    private Node<T> head;
    private Comparator<T> comp;
    private int numItems;
    
    //sub class Node
    private class Node<T> {
        private T kv;
        private Node<T> leftChild;
        private Node<T> rightChild;
        private Node<T> parent;
     

        //constructor initialize a node with no child and parent
        public Node (T kv) {
            this.kv = kv;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
            
        }

        //return the thing stored in the node
        public T getThing() {
            return this.kv;
        }

        //return its left child
        public Node<T> getLeft() {
            return this.leftChild;
        }

        //set its left child
        public void setLeft(Node left) {
            this.leftChild = left;
        }

        //return its right child
        public Node<T> getRight() {
            return this.rightChild;
        }

        //set its right child
        public void setRight(Node right) {
            this.rightChild = right;
        }

        //return its parent
        public Node<T> getParent() {
            return this.parent;
        }

        //set its parent
        public void setParent(Node parent) {
            this.parent = parent;
        }
    
    }
    //end of Node class


    //constructor
    public NodeBasedHeap(Comparator<T> comp) {
        numItems = 0;
        head = null;
        this.comp = comp;
    }


    //return number of nodes in the heap
    public int size() {
        return this.numItems;
    }

    //add the node to the heap
    public void add(T obj) {
        numItems ++; 
        Node n = new Node(obj);
        if (head == null) {
            this.head = n;
        } else {
            nextFreePosition(n);
            percolateUp(n);
        }
              
    }

    //find the next free position for the new node to be added
    public void nextFreePosition(Node n) {
        //find depth
        int level = (int)(Math.log(numItems)/Math.log(2));
        //find number of nodes on the last level
        int nodesOnLastLevel = (int)Math.pow(2.0, level);
        //find the next free space on that level
        int position = numItems - nodesOnLastLevel + 1;
        //find which half it belongs to to decide to go left or right
               boolean goLeft = true;
        if (position > nodesOnLastLevel/2) {
            goLeft = false;
        }        
        
        Node temp = head;
        
        while (level != 1) {            
            if (goLeft == true) {    
                temp = temp.getLeft();
            } else {
                position = position - (nodesOnLastLevel /2);
                temp = temp.getRight();
            }
            nodesOnLastLevel = nodesOnLastLevel /2;
            goLeft = true;
            if (position > nodesOnLastLevel/2) {
                goLeft = false;
            }
            level --;
        }
        //for the last level, we have to SET the parent for the node and set the node to the parent's child
        n.setParent(temp);
        if (temp.getLeft() == null) {
            temp.setLeft(n);

        } else {
            temp.setRight(n);

        }
    }

    //compare the added node to its parent to ensure heap structure
    public void percolateUp(Node n) {
        Node parent = n.getParent();
        if (parent != null) {
            int c = this.comp.compare((T)(n.getThing()), (T)(parent.getThing()));
            if (c > 0) {
                swap(parent, n);  
                percolateUp(n);
                
            } else {
                return;
            }     
        }
        else  {
            head = n;
            return;
        }           
    }

    //remove the top node from the heap
    public T remove() {
        if (numItems == 0) {
            return null;
        }
        T remove = head.getThing();
        Node temp = lastPosition();
        temp.setLeft(head.getLeft());
        temp.setRight(head.getRight());
        head = temp;        
        boolean firstTime = true;
        percolateDown(head, firstTime);
        numItems --;
        return remove;
    }

    //find the last node on the heap
    public Node lastPosition() {
        //find depth
        int level = (int)(Math.log(numItems)/Math.log(2));
        //find number of nodes on the last level
        int nodesOnLastLevel = (int)Math.pow(2.0, level);
        //find the next free space on that level
        int position = numItems - nodesOnLastLevel + 1;
        //find which half it belongs to to decide to go left or right       
        boolean goLeft = true;
        if (position > nodesOnLastLevel/2) {
            goLeft = false;
        }        
        Node temp = head;      
        while (level != 0) {
            if (goLeft == true) {
                temp = temp.getLeft();
            } else {
                position = position - (nodesOnLastLevel /2);
                temp = temp.getRight();
            }
            nodesOnLastLevel = nodesOnLastLevel /2;
            goLeft = true;
            if (position > nodesOnLastLevel/2) {
                goLeft = false;
            }
            level --;
        }
        if (temp.getParent().getRight() == temp) {
            temp.getParent().setRight(null);
        }
        else if (temp.getParent().getLeft() == temp){
            temp.getParent().setLeft(null);
        }
        Node n = new Node(temp.getThing());
        return n;
    }

    //compare the node to its children to find the right position to ensure heap structure
    public void percolateDown(Node n, boolean firstTime) {
        if (n.getLeft() != null) {
            Node biggerChild = n.getLeft();
            if (n.getRight() != null) {
                int bigger = this.comp.compare((T)biggerChild.getThing(), (T)(n.getRight().getThing()));
                if (bigger < 0 ) {
                    biggerChild = n.getRight();
                } 
            }
            
            int c = this.comp.compare((T)n.getThing(), (T)(biggerChild.getThing()));
            if (c < 0  ) {
                if (firstTime == true) {
                    this.head = biggerChild;
                    firstTime = false; 
                }          
                swap(n, biggerChild);
                percolateDown(n, firstTime);
            } else {
                return;
            }

        } 
        else {
            return;
        }

    }

    //swap node a's and b's positions
    public void  swap(Node a, Node b) {
        boolean left = true;
        Node aParent = a.getParent();
        Node aChild = a.getLeft();

        if (aChild == b) {
            aChild = a.getRight();
            left = false;
        }

        Node bLeft = b.getLeft();
        Node bRight = b.getRight();

        a.setLeft(bLeft);
        if (bLeft != null)
        bLeft.setParent(a);
        
        a.setRight(bRight);
        if (bRight != null)
        bRight.setParent(a);
        a.setParent(b);

        b.setParent(aParent);
        if (a != null)
        a.setParent(b);
        if (aChild != null)
        aChild.setParent(b);
        
        if (left == false) {
            b.setLeft(a);
            b.setRight(aChild);
            
        } else {
            
            b.setLeft(aChild);
            b.setRight(a);
            
        }
        if (b.getParent() != null) {
            if (b.getParent().getLeft() == b.getLeft() || b.getParent().getLeft() == b.getRight()) 
                b.getParent().setLeft(b);

            else if (b.getParent().getRight() == b.getLeft() || b.getParent().getRight() == b.getRight())
                b.getParent().setRight(b);
        }
    }

    //toString method prints out heap structure level by level
    public String toString() {
        String layer = "";
        if (head != null) {
            return byLayer(head);
        }
        return layer;
    }

    

    public String byLayer(Node node) {        
        String structure = "";
        MyQueue<Node> queue = new MyQueue<Node>();
        queue.offer(node);
        queue.offer(null);
        while (queue.getSize() > 1) {
            Node parent = queue.poll();           
            if (parent != null) {
                structure += parent.kv + "\t";
                if (parent.leftChild != null) {
                    queue.offer(parent.leftChild);
                } 
                if (parent.rightChild != null) {
                    queue.offer(parent.rightChild);
                } 
            } else {
                structure += "\n" ;
                queue.offer(null);
            }  
        }

        return structure;

    }

   

    public static void main(String[] args) {
        NodeBasedHeap<KeyValuePair<String,Integer>> test = new NodeBasedHeap<KeyValuePair<String,Integer>>(new CompareValue());
        test.add(new KeyValuePair("red", 5));
        test.add(new KeyValuePair("blue", 10));
        test.add(new KeyValuePair("yellow", 15));
        test.add(new KeyValuePair("pink", 20));
        test.add(new KeyValuePair("green", 25));
        test.add(new KeyValuePair("black", 30));
        test.add(new KeyValuePair("orange", 35));
        test.add(new KeyValuePair("purple", 40));
        test.add(new KeyValuePair("gray", 45));
        test.add(new KeyValuePair("white", 50));        
        test.add(new KeyValuePair("REP1", 25));       
        test.add(new KeyValuePair("REP2", 35));
       test.add(new KeyValuePair("brown", 55));
        test.add(new KeyValuePair("a", 60));
        test.add(new KeyValuePair("b", 65));
        test.add(new KeyValuePair("c", 70));
        test.add(new KeyValuePair("d", 75));
        test.add(new KeyValuePair("e", 80));
        test.add(new KeyValuePair("f", 85));
        test.add(new KeyValuePair("REP3", 85));     
        System.out.println(test);
        for (int i = 0; i < 6; i ++)
        System.out.println("remove " + test.remove());
        System.out.println("After removing 6" + "\n" + test);

    }


}