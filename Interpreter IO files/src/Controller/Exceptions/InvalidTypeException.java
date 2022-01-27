package Controller.Exceptions;

public class InvalidTypeException extends MyException {
    public InvalidTypeException(Class<?> expected, Class<?> actual) {
        super("Expected %s, but got %s !".formatted(expected.toString(), actual.toString()));
    }
}

