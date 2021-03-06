package Model.Containers;

import Controller.Exceptions.MyException;
import Values.IValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary<Key, Value> implements IDictionary<Key, Value> {
    public HashMap<Key, Value> Map = new HashMap<Key, Value>();

    public Dictionary(){
    }
    public Dictionary(HashMap<Key, Value> map) {
        this.Map = map;
    }

    @Override
    public void add(Key key, Value value) {
        this.Map.put(key, value);
    }

    @Override
    public Value get(Key key) throws MyException {
        if (!Map.containsKey(key)) {
            throw new MyException("Key not found");
        }
        return Map.get(key);
    }

    @Override
    public void replace(Key key, Value value) {
        this.Map.replace(key, value);
    }

    @Override
    public void put(Key key, Value value) {
        this.Map.put(key, value);
    }

    @Override
    public boolean has(Key key) {
        return this.Map.containsKey(key);
    }

    public Set<java.util.Map.Entry<Key, Value>> entrySet() {
        return this.Map.entrySet();
    }

    @Override
    public String toFileString() {
        return "%s".formatted(String.join("", this.Map.entrySet().stream().map(entry -> "%s --> %s\n".formatted(entry.getKey(), entry.getValue())).toArray(String[]::new)));
    }

    @Override
    public Collection<Value> values() {
        return this.Map.values();
    }

    @Override
    public void remove(Key key) {
        this.Map.remove(key);
    }

    @Override
    public String toString() {
        return "{%s}".formatted(String.join(", ", this.Map.entrySet().stream().map(entry -> "%s -> %s".formatted(entry.getKey(), entry.getValue())).toArray(String[]::new)));
    }

    @Override
    public Dictionary<Key, Value> deepCopy(){
        Dictionary<Key, Value> new_dict = new Dictionary<Key, Value>();
        for (HashMap.Entry<Key, Value> entry : Map.entrySet()) {
            new_dict.put(entry.getKey(), entry.getValue());
        }
        return new_dict;
//        HashMap<Key, Value> copy = new HashMap<Key, Value>();
//        for (Map.Entry<Key, Value> entry : this.Map.entrySet())
//        {
//            copy.put(entry.getKey(), entry.getValue().);
//        }
//        return new Dictionary<Key, Value>(copy);
//        return new Dictionary<Key, Value>((HashMap<Key, Value>) this.Map.clone());
    }
}
