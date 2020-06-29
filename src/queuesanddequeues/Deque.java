package queuesanddequeues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node rear;
    private int size;

    private class Node { // doubly linked list
        Item item;
        Node next;
        Node last;

        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.last = null;
        }
    }

    // construct an empty deque
    public Deque() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return front == null; // see if there is nothing at the start of the array
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (isEmpty()) {
            front = new Node(item);
            rear = front;
        } else {
            Node newFront = new Node(item); // create a new first node
            newFront.next = this.front; // link the next of the new node to the old front
            this.front.last = newFront; // set the old front's last to the new front
            newFront.last = null; // set the last to null to indicate that this is the new front
            this.front = newFront; // point to the new front
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (isEmpty()) {
            rear = new Node(item);
            front = rear;
        } else {
            Node newRear = new Node(item); // create a new last node
            rear.next = newRear; // link the rear's next to the new last
            newRear.last = rear; // link the new rear's last to the old rear
            this.rear = newRear; // point to the new rear
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = front.item; // pull the value from the front item
        if (size == 1) {
            front = null;
            rear = null;
        } else {
            front = front.next; // move the front up
            front.last = null; // remove the pointer to the old front
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = rear.item; // pull the value from the rear item
        if (size == 1) {
            front = null;
            rear = null;
        } else {
            rear = rear.last; // move the rear back
            rear.next = null; // remove the pointer to the old rear
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = front; // first item in the list

        public boolean hasNext() { // if done return true; if not done return false
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> values = new Deque<>();
        values.addFirst("Value 1");
        values.addFirst("Value 2");
        values.addLast("Value 3");
        values.addLast("Value 4");

        for (String s : values)
            System.out.println(s);
        System.out.println("");

        values.removeFirst();
        values.removeLast();

        for (String s : values)
            System.out.println(s);
        System.out.println("");

        values.removeLast();
        values.removeFirst();

        for (String s : values)
            System.out.println(s);
        System.out.println("");

        try {
            values.addFirst(null);
        } catch (NullPointerException e) {
            System.out.println("TESTING: .addFirst()");
            System.out.println(e);
        }

        try {
            values.addLast(null);
        } catch (NullPointerException e) {
            System.out.println("TESTING: .addLast()");
            System.out.println(e);
        }

        try {
            values.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println("TESTING: .removeFirst()");
            System.out.println(e);
        }

        try {
            values.removeLast();
        } catch (NoSuchElementException e) {
            System.out.println("TESTING: .removeLast()");
            System.out.println(e);
        }

        values.addFirst("Value 1");
        values.addFirst("Value 2");
        values.addFirst("Value 3");
        values.addFirst("Value 4");

        for (String s : values)
            System.out.println(s);
        System.out.println("");
    }

}