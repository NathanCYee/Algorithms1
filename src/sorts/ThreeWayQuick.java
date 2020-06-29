package sorts;

import edu.princeton.cs.algs4.StdRandom;

public class ThreeWayQuick {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a); // shuffle needed for performance guarantee
        sort(a, 0, a.length - 1); // recursive sorting algorithm
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return; // break out of the recursive cycle when hi and lo meet or cross
        int lt = lo, gt = hi;
        Comparable v = a[lo]; // partitioning element
        int i = lo; // iterator value
        while (i <= gt) { // while the iterator and gt don't cross
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exchange(a, lt++, i++);
            else if (cmp > 0) exchange(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt - 1); // repeat for <v
        sort(a, gt + 1, hi); // repeat for >v
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

    public static Comparable Select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1; // do our partitioning
        while (hi > lo) {
            // go in one subarray or another depending on where j is
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1; // if k is less than j, do the left subfile
            else if (j > k) hi = j - 1; // if k is right than j, do the right subfile
            else return a[k]; // if j = k, we are done and k is the largest
        }
        return a[k];
    }
}
