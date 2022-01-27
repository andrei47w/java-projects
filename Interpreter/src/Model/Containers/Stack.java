package Model.Containers;

import java.util.Iterator;

public class Stack<T> implements IStack<T> {
    private java.util.Stack<T> innerStack = new java.util.Stack<>();

    public Stack() {
    }

    public Stack(java.util.Stack<T> innerStack) {
        this.innerStack = innerStack;
    }

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
        return this.innerStack.toString();
    }

    public Iterator<T> iterator() {
        return this.innerStack.iterator();
    }

    public Stack<T> deepCopy() {
        java.util.Stack<T> newStack = new java.util.Stack<T>();
        newStack.addAll(this.innerStack);
        return new Stack<T>(newStack);
    }

    @Override
    public T top() {
        return innerStack.peek();
    }

    @Override
    public java.util.Stack<T> getStack() {
        return this.innerStack;
    }
}
