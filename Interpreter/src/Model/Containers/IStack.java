package Model.Containers;


public interface IStack<T> {
    T pop();

    void push(T value);

    boolean isEmpty();
}