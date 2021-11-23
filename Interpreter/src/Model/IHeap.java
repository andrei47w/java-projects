package Model;

import Values.IValue;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public interface IHeap<IValue> {

    IValue lookup(String symbolName);

    Map.Entry<Heap, AtomicInteger> allocate(IValue v);

    IValue lookup(AtomicInteger address);

    Map<AtomicInteger, IValue> getContent();

    IValue getValue(AtomicInteger key);

    int getFreeLocation();

    void add(IValue value);

    void update(AtomicInteger key, IValue value);

    void remove(AtomicInteger key);

    void setContent(Map<AtomicInteger, IValue> newContent);
}
