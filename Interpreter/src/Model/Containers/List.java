package Model.Containers;

import Controller.Exceptions.InvalidIndexException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class List<T> implements IList<T> {
    private final ArrayList<T> innerList = new ArrayList<>();


    @Override
    public void append(T element) {
        this.innerList.add(element);
    }

    @Override
    public T removeAt(int position) throws InvalidIndexException {
        try {
            return this.innerList.remove(position);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidIndexException(position);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.innerList.size() == 0;
    }

    @Override
    public T get(int position) throws InvalidIndexException {
        try {
            return this.innerList.get(position);
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidIndexException(position);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return this.innerList.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.innerList.forEach(action);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.innerList.toArray());
    }
}
