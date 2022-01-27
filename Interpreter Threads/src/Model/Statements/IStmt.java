package Model.Statements;

import Controller.Exceptions.*;
import Model.PrgState;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStmt {
    PrgState exec(PrgState state) throws MyException, IOException, HeapException, ExpressionException;

    String toFileString();

    public IStmt deepCopy();
}