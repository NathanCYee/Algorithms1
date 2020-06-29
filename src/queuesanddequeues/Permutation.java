package queuesanddequeues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int toDisplay = Integer.parseInt(args[0]); // Get the input amount

        // Read the input data into the queue
        RandomizedQueue<String> data = new RandomizedQueue<>();
        while (!StdIn.isEmpty())
            data.enqueue(StdIn.readString());

        // Output the data for the amount given
        for (int i = 0; i < toDisplay; i++)
            StdOut.println(data.dequeue());
    }
}
