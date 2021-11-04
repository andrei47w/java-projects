package Controller.Exceptions;

public class InvalidIndexException extends MyException {
    public InvalidIndexException(int index) {
        super("Index %d is invalid!".formatted(index));
    }
}
