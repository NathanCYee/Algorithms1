package symbol;

public class SeparateChainingHashST<Key, Value> {
    /* array doubling and halving code omitted */
    private int M = 97; // number of chains
    private Node[] st = new Node[M]; // array of chains

    private static class Node {
        private Object key; // no generic array creation
        private Object val; // declare key and value of type Object
        private Node next;

        Node(Object key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        int i = hash(key); // get hashcode for key
        for (Node x = st[i]; x != null; x = x.next) // get the item at the hashcode, traverse down if collision until found
            if (key.equals(x.key)) return (Value) x.val;
        return null;
    }

    public void put(Key key, Value val) {
        int i = hash(key); // get hashcode for key
        for (Node x = st[i]; x != null; x = x.next) // get the item at the hashcode, traverse down if collision until found
            if (key.equals(x.key)) { // break if you have a new index and change the val
                x.val = val;
                return;
            }
        st[i] = new Node(key, val, st[i]); // add a new node at the front of the node stack linking to the old
    }
}

