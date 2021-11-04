package Model.Containers;

import java.util.Arrays;

public class Stack<T> implements IStack<T> {
    private final java.util.Stack<T> innerStack = new java.util.Stack<>();

    @Override
    public T pop() {
        return this.innerStack.pop();
    }

    @Override
    public void push(T value) {
        this.innerStack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return this.innerStack.size() == 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.innerStack.toArray());
    }
}
