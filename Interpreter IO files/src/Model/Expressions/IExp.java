package Model.Expressions;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Model.Containers.IDictionary;
import Values.IValue;

public interface IExp {
    IValue eval(IDictionary<String, IValue> symbolTable) throws InvalidTypeException, MissingKeyException;

    public IExp deepCopy();
}