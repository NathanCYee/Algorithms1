package stack;

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> { // Generic type Item
    private Node first = null;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first; // first item in the list

        public boolean hasNext() { // if done return true; if not done return false
            return current != null;
        }

        public void remove() {
            /* not supported */
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
