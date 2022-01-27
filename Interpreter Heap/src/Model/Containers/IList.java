package Model.Containers;

import Controller.Exceptions.InvalidIndexException;

import java.util.function.Consumer;

public interface IList<T> extends Iterable<T> {
    void append(T element);

    T removeAt(int position) throws InvalidIndexException;

    boolean isEmpty();

    T get(int position) throws InvalidIndexException;

    @Override
    void forEach(Consumer<? super T> action);

    int size();

    public String toFileString();

    IList<T> deepCopy();
}
