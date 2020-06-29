package sorts;

import edu.princeton.cs.algs4.StdRandom;

public class Shuffle {
    public static void shuffle(Object[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            exchange(a, i, StdRandom.uniform(i + 1)); // random between 0 and i
    }

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
