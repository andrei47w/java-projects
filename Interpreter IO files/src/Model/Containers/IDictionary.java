package Model.Containers;

public interface IDictionary<Key, Value> {
    void add(Key key, Value value);

    void replace(Key key, Value value);

    Value get(Key key);

    boolean has(Key key);

    String toFileString();

    void remove(Key key);

    IDictionary<Key,Value> deepCopy();
}
