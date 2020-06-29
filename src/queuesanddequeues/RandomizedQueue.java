package queuesanddequeues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] values;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        values = (Item[]) new Object[1];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (values.length == N) resize(2 * values.length);

        values[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(N);
        while (values[index] == null) index = StdRandom.uniform(N);

        Item data = values[index];
        values[index] = null;
        N--;
        if (N > 0 && N == values.length / 4) resize(values.length / 2);

        return data;
    }

    private void resize(int capacity) {
        Item[] copyData = (Item[]) new Object[capacity];
        int i = 0;
        for (Item item : values) if (item != null) copyData[i++] = item;
        values = copyData;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(N);
        while (values[index] == null) index = StdRandom.uniform(N);

        return values[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandIterator();
    }

    private class RandIterator implements Iterator<Item> {
        private Item[] displayValues;
        private int displayed = 0;

        public RandIterator() {
            displayValues = (Item[]) new Object[N];
            int i = 0;
            for (Item item : values) if (item != null) displayValues[i++] = item;
            StdRandom.shuffle(displayValues);
        }

        public boolean hasNext() {
            return displayed != N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();

            int index = StdRandom.uniform(N);
            while (displayValues[index] == null) index = StdRandom.uniform(N);

            Item data = displayValues[index];
            displayValues[index] = null;
            displayed++;

            return data;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        for (int i = 1; i <= 5; i++)
            test.enqueue(i);

        System.out.println(test.sample());
        System.out.println("");

        for (int i : test)
            System.out.println(i);

        System.out.println("");

        System.out.println(test.dequeue());
        System.out.println("");
        for (int i : test)
            System.out.println(i);

        System.out.println("");

        while (!test.isEmpty())
            test.dequeue();

        for (int i : test) // bug when empty
            System.out.println(i);
        System.out.println("");

        try {
            test.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("TESTING: .dequeue()");
            System.out.println(e);
        }

        try {
            test.enqueue(null);
        } catch (NullPointerException e) {
            System.out.println("TESTING: .dequeue()");
            System.out.println(e);
        }

        System.out.println("");
        for (int i = 6; i <= 10; i++)
            test.enqueue(i);

        for (int i : test)
            System.out.println(i);
    }
}