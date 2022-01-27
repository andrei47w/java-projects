package Model.Expressions;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Heap;
import Values.IValue;

public interface IExp {
    IValue eval(IDictionary<String, IValue> symbolTable) throws MyException, ExpressionException;

    public IExp deepCopy();

    IValue eval(IDictionary<String, IValue> symbols, Heap heap) throws ExpressionException, MyException;
}