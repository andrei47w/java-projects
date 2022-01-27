package Model.Containers;

import java.util.HashMap;

public class Dictionary<Key, Value> implements IDictionary<Key, Value> {
    private final HashMap<Key, Value> Map = new HashMap<Key, Value>();

    @Override
    public void add(Key key, Value value) {
        this.Map.put(key, value);
    }

    @Override
    public Value get(Key key) {
        return this.Map.get(key);
    }

    @Override
    public void replace(Key key, Value value) {
        this.Map.replace(key, value);
    }

    @Override
    public boolean has(Key key) {
        return this.Map.containsKey(key);
    }

    @Override
    public String toString() {
        return "{%s}".formatted(String.join(", ", this.Map.entrySet().stream().map(entry -> "%s -> %s".formatted(entry.getKey(), entry.getValue())).toArray(String[]::new)));
    }
}
