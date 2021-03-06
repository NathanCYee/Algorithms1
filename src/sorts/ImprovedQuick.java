package sorts;

import edu.princeton.cs.algs4.StdRandom;

public class ImprovedQuick {
    private static int CUTOFF = 10;

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a); // shuffle needed for performance guarantee
        sort(a, 0, a.length - 1); // recursive sorting algorithm
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF - 1) { // insertion sort is more efficient for small subarrays
            Insertion.sort(a, lo, hi);
            return;
        }
        if (hi <= lo) return; // break out of the recursive cycle when hi and lo meet or cross
        int j = partition(a, lo, hi); // gather the index of the variable that the layer is partitioned around
        sort(a, lo, j - 1); // repeat for lower
        sort(a, j + 1, hi); // repeat for upper
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1; // initiate pointers at each end of the array
        while (true) {
            // find item on left to swap
            while (less(a[++i], a[lo]))
                if (i == hi) break; // test to make sure you don't run off the right end of the array

            // find item on right to swap
            while (less(a[lo], a[--j]))
                if (j == lo) break; // test to make sure you don't run off the left end of the array

            // if they are crossed, break out of the loop and exchange the pointer into position
            if (i >= j) break; // check if pointers cross
            exchange(a, i, j); // swap
        }

        // swap with partitioning item
        exchange(a, lo, j);
        return j; // return the index of the item now known to be in place
    }
}
