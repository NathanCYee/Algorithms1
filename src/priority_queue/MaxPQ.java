package priority_queue;

public class MaxPQ<Key extends Comparable<Key>> { // key must ve Comparable(bounded type parameter)
    private Key[] pq;
    private int N;

    public MaxPQ(int capacity) { // create an empty priority queue
        pq = (Key[]) new Comparable[capacity + 1];
    }

    /*
    MaxPQ(Key[] a) {
    } // create a priority queue with given keys
*/
    // Priority Queue Operations
    public void insert(Key key) { // insert a key into the priority queue
        pq[++N] = key;
        swim(N);
    }

    public Key delMax() { // return and remove the largest key
        Key max = pq[1]; // store the max value to return
        exchange(1, N--); // exchange the end with the tail and decrement the tail size
        sink(1); // demote the new head down to position
        pq[N + 1] = null; // prevent loitering
        return max;
    }

    public boolean isEmpty() {  // is the priority queue empty?
        return this.N == 0;
    }

    // heap.Heap Helper Functions
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k; // compareTo value
            if (j < N && less(j, j + 1)) j++; // get the larger node
            if (!less(k, j)) break; // if k is no longer less than the j, break
            exchange(k, j); // otherwise, keep exchanging
            k = j; // set the trace index to the new index
        }
    }

    // Array Helper Functions
    private boolean less(int v, int w) {
        return pq[v].compareTo(pq[w]) < 0;
    }

    private void exchange(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
    /*
    public Key max() { // returns the largest key
    }

    public int size() {
    } // number of entries in the priority queue
    */
}
