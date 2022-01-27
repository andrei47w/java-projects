package Model.Containers;

import Controller.Exceptions.MyException;
import Values.IValue;

import java.util.Collection;

public interface IDictionary<Key, Value> {
    void add(Key key, Value value);

    void replace(Key key, Value value);

    Value get(Key key) throws MyException;

    boolean has(Key key);

    String toFileString();

    Collection<Value> values();

    void remove(Key key);

    IDictionary<Key,Value> deepCopy();
}
