package Model.Containers;

import Model.Heap;

import java.util.concurrent.atomic.AtomicInteger;

public interface ITuple<F, S> {
    F getFirst();
    S getSecond();

    Heap getKey();

    AtomicInteger getValue();

    AtomicInteger setValue(AtomicInteger value);
}
