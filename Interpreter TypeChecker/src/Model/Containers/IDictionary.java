package Model.Containers;

import Controller.Exceptions.MyException;
import Types.IType;
import Values.IValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<Key, Value> {
    void add(Key key, Value value);

    void replace(Key key, Value value);

    Value get(Key key) throws MyException;

    void put(Key key, Value value);

    boolean has(Key key);

    public Set<Map.Entry<Key, Value>> entrySet();

    String toFileString();

    Collection<Value> values();

    void remove(Key key);

    IDictionary<Key,Value> deepCopy();
}
