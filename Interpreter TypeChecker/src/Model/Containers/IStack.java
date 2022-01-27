package Model.Containers;


import java.util.Iterator;
import java.util.function.Consumer;

public interface IStack<T> {
    T pop();

    void push(T value);

    boolean isEmpty();

    Iterator<T> iterator();

    public IStack<T> deepCopy();

    T top();
}