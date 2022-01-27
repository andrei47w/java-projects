package Controller.Exceptions;

public class UndeclaredVariableException extends MyException {
    public UndeclaredVariableException(String identifier) {
        super("Undeclared variable %s !".formatted(identifier));
    }
}
