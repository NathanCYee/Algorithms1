package stack;

public class FixedCapacityStackOfStrings {
    private String[] values;
    private int N = 0;

    public FixedCapacityStackOfStrings(int capacity) {
        values = new String[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        values[N++] = item; // get the index of the array then increment N
    }

    public String pop() {
        /* Allow loitering (value persists)
        return values[--N]; // decrement N then access the index in the array
         */
        String item = values[--N];
        values[N] = null; // remove the item so that it does not take up memory
        return item;
    }
}
