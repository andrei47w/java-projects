package Model.Statements;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.UndeclaredVariableException;
import Controller.Exceptions.VariableAlreadyDeclaredException;
import Model.PrgState;

public interface IStmt {
    void exec(PrgState state) throws VariableAlreadyDeclaredException, InvalidTypeException, UndeclaredVariableException, MissingKeyException;
}