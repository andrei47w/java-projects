package Controller.Exceptions;

public class EmptyStackException extends MyException {
    public EmptyStackException() {
        super("Stack is empty!");
    }
}
