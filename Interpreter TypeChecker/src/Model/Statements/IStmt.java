package Model.Statements;

import Controller.Exceptions.*;
import Model.Containers.IDictionary;
import Model.PrgState;
import Types.IType;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStmt {
    PrgState exec(PrgState state) throws MyException, IOException, HeapException, ExpressionException;

    String toFileString();

    public IStmt deepCopy();

    public IDictionary<String, IType> typecheck(IDictionary<String,IType> typeEnv) throws MyException;
}