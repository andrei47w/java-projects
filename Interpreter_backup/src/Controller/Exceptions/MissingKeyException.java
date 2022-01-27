package Controller.Exceptions;

public class MissingKeyException extends MyException {

    public MissingKeyException(Object key) {
        super("Key %s was not found!".formatted(key));
    }
}
