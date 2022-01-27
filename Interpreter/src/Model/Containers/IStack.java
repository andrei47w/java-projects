package Model.Containers;


import java.util.Iterator;
import java.util.Stack;

public interface IStack<T> {
    T pop();

    void push(T value);

    boolean isEmpty();

    Iterator<T> iterator();

    public IStack<T> deepCopy();

    T top();

    Stack<T> getStack();
}