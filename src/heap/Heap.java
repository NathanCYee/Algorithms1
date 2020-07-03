package heap;

public class Heap {

    public static void sort(Comparable[] a) {
        int N = a.length;
        // Build heap using bottom-up method
        for (int k = N / 2; k >= 1; k--)
            sink(a, k, N);

        // swap the head with the end of the range and decrement the usable range in the array
        // sink the new head
        while (N > 1) {
            exchange(a, 1, N);
            sink(a, 1, --N);
        }
    }

    private static void sink(Comparable[] a, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(a, j, j + 1)) j++;
            if (!less(a, k, j)) break;
            exchange(a, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
