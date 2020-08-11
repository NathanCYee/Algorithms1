package symbol;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root; // root of BST

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count; // number of nodes in subtree

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }

        public int size() {
            return size(root);
        }

        private int size(Node x) {
            if (x == null) return 0;// ok to call when x is null
            return x.count;
        }
    }

    public void put(Key key, Value val) {  /* Associate value with key */
        root = put(root, key, val);
    }

    // Recursive code
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val); // append if nothing is there
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val; // if you hit the value, set
        //x.count = 1 + size(x.left) + size(x.right);
        return x; // seek up the recursive tree
    }

    public Value get(Key key) {  /* Return value corresponding to given key, or null if no such key */
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else if (cmp == 0) return x.val;
        }
        return null;
    }

    public void delete(Key key) {  /* see next slides */ }

    public Iterable<Key> iterator() {  /* see next slides */
        return null;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null; // cannot run floor if nothing is found
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null; // if you find a null floor, terminate the stack
        int cmp = key.compareTo(x.key);

        if (cmp == 0) return x; // if key is the same as the node, you found the floor

        if (cmp < 0)
            return floor(x.left, key); // if the key is less than the node, move the head to the left and repeat

        Node t = floor(x.right, key); // get the floor of the node larger
        if (t != null) return t;
        else return x; // previous node is the best case for floor
    }
}
