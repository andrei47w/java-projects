package Model.Containers;

import Controller.Exceptions.InvalidIndexException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MyList<T> implements IList<T> {
    private ArrayList<T> innerList = new ArrayList<>();

    public MyList() {
    }

    public MyList(ArrayList<T> innerList) {
        this.innerList = innerList;
    }


    public boolean addAll(List<T> list){
        return this.innerList.addAll(list);
    }


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
    public int size() {
        return this.innerList.size();
    }

    @Override
    public Stream<T> stream() {
        return this.innerList.stream();
    }

    @Override
    public String toString() {
        return Arrays.toString(this.innerList.toArray());
    }

    public String toFileString() {
        String str = "";
        for (T t : this.innerList) str += t + "\n";

        return str;
    }

    @Override
    public MyList<T> deepCopy() {
        return new MyList<T>((ArrayList<T>) this.innerList.clone());
    }
}
