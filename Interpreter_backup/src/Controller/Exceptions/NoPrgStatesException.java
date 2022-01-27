package Controller.Exceptions;

public class NoPrgStatesException extends MyException {
    public NoPrgStatesException() {
        super("There are no program states left!");
    }
}
