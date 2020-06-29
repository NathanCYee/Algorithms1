package sorts;

public class Shell {
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1; // 3x + 1 increment sequence

        while (h >= 1) { // h-sort the array
            for (int i = h; i < N; i++) // insertion sort
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) // if a value is found, move it back h amount
                    exchange(a, j, j - h);
            h = h / 3; // move to next increment
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
