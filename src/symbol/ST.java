package symbol;

abstract class ST<Key, Value> {
    public abstract void put(Key key, Value val); // put a key-value pair into the table

    public abstract Value get(Key key); // value paired with key

    public abstract void delete(Key key); // remove key+value from table

    public abstract boolean contains(Key key); // is there a value paired with key?

    public abstract boolean isEmpty(); // is the table empty?

    public abstract int size(); // number of key-value pairs in the table

    public abstract Iterable<Key> keys(); // all the keys in the table
}
