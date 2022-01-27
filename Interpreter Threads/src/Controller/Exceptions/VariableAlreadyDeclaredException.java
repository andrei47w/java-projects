package Controller.Exceptions;

public class VariableAlreadyDeclaredException extends MyException {
    public VariableAlreadyDeclaredException(String identifier) {
        super("Variable %s was already declared!".formatted(identifier));
    }
}
