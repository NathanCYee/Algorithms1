package stack;

public class ResizingArrayStackOfStrings {
    private String[] values;
    private int N = 0;

    public ResizingArrayStackOfStrings() {
        values = new String[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        if (N == values.length)
            resize(2 * values.length);
        values[N++] = item; // get the index of the array then increment N
    }

    private void resize(int capacity) {
        String[] copyData = new String[capacity];
        for (int i = 0; i < N; i++)
            copyData[i] = values[i];
        values = copyData;
    }

    public String pop() {
        String item = values[--N];
        values[N] = null; // remove the item so that it does not take up memory
        if (N > 0 && N == values.length / 4)
            resize(values.length / 2);
        return item;
    }
}
