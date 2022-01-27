package Model.Containers;

import Model.Heap;

import java.util.concurrent.atomic.AtomicInteger;

public record Tuple<F, S>(F first, S second) implements ITuple<F, S> {
    @Override
    public F getFirst() {
        return first;
    }

    @Override
    public S getSecond() {
        return second;
    }

    @Override
    public Heap getKey() {
        return null;
    }

    @Override
    public AtomicInteger getValue() {
        return null;
    }

    @Override
    public AtomicInteger setValue(AtomicInteger value) {
        return null;
    }
}
