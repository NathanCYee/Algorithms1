package sorts;

public class MergeBottomUp {
    private static Comparable[] aux;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) // iterate through the sizes 1, 2, 4, 8, 16...
            for (int lo = 0; lo < N - sz; lo += sz + sz) // for each group of subarray, merge the two next to each other
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
    }


    private static boolean isSorted(Comparable[] main, int min, int max) {
        Comparable prev = main[min];
        for (int i = min; i <= max; i++) {
            if (less(main[i], prev)) return false;
            prev = main[i];
        }
        return true;
    }

    private static void merge(Comparable[] main, int lo, int mid, int hi) {
        // Assert what you expect in
        assert isSorted(main, lo, mid); // Assert the precondition that a[lo..mid] is sorted
        assert isSorted(main, mid + 1, hi); // Assert the precondition that a[mid+1..hi] is sorted

        // Array copy into aux
        for (int k = lo; k <= hi; k++)
            aux[k] = main[k];

        // Merge the aux array back into a
        int i = lo, j = mid + 1; // define pointers for the two arrays
        for (int k = lo; k <= hi; k++) {
            if (i > mid) main[k] = aux[j++]; // if you've gone through all of the left side, only run for right side
            else if (j > hi) main[k] = aux[j++]; // if you've gone through all of the right side, only run for left side
            else if (less(aux[j], aux[i])) main[k] = aux[j++]; // if j index is less than i index, append and increment
            else main[k] = aux[i++]; // default assumption to always take the first if both are same or first is less
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
