package sorts;

public class Merge {
    private static int CUTOFF = 7;

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] main, Comparable[] aux, int lo, int hi) {
        // Too much overhead for small arrays, use insertion sort
        if (hi <= lo + CUTOFF - 1) {
            Insertion.sort(main, lo, hi);
            return;
        }

        if (hi < lo) return; // do not run if parameters are improper

        // This runs until individual arrays are length 1, then it merges it back
        int mid = lo + (hi - lo) / 2;

        // split the arrays
        sort(aux, main, lo, mid);
        sort(aux, main, mid + 1, hi);

        // if the largest left side is less than the smallest right side, array is already sorted
        if (!less(main[mid + 1], main[mid])) return;

        // merge the arrays
        merge(main, aux, lo, mid, hi);
    }

    private static boolean isSorted(Comparable[] main, int min, int max) {
        Comparable prev = main[min];
        for (int i = min; i <= max; i++) {
            if (less(main[i], prev)) return false;
            prev = main[i];
        }
        return true;
    }

    private static void merge(Comparable[] main, Comparable[] aux, int lo, int mid, int hi) {
        // Assert what you expect in
        assert isSorted(main, lo, mid); // Assert the precondition that a[lo..mid] is sorted
        assert isSorted(main, mid + 1, hi); // Assert the precondition that a[mid+1..hi] is sorted

        // Array copy into aux
        for (int k = lo; k <= hi; k++)
            aux[k] = main[k];

        int i = lo, j = mid + 1; // define pointers for the two arrays
        for (int k = lo; k <= hi; k++) {
            if (i > mid) aux[k] = main[j++]; // if you've gone through all of the left side, only run for right side
            else if (j > hi) aux[k] = main[j++]; // if you've gone through all of the right side, only run for left side
            else if (less(main[j], main[i])) aux[k] = main[j++]; // if j index is less than i index, append and increment
            else aux[k] = main[i++]; // default assumption to always take the first if both are same or first is less
        }

        // Assert what you think it's going to do
        assert isSorted(main, lo, hi); // Assert that the entire array is sorted
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
