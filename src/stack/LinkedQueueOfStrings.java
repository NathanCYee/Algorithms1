package stack;

public class LinkedQueueOfStrings {
    private Node first = null;
    private Node last = null;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(String item) {
        Node oldlast = last; // create a pointer to the old last object
        last = new Node(); // create a new node to be the new last
        last.item = item; // add the value
        last.next = null; // indicate the end of the linkedlist by setting the next to null (nothing before it)

        // special cases for empty queues
        if (isEmpty())
            first = last;
        else
            oldlast.next = last; // link it to the current last
    }

    public String dequeue() {
        String item = first.item;
        first = first.next;
        if (isEmpty())
            last = null;
        return item;
    }
}
