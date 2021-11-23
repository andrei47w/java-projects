package Model.Expressions;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Heap;
import Values.IValue;

public record VariableExp(String key) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) throws MyException {
        return symbolTable.get(this.key);
    }

    @Override
    public VariableExp deepCopy() {
        return new VariableExp(this.key);
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbols, Heap heap) throws ExpressionException, MyException {
        return null;
    }

    @Override
    public String toString() {
        return this.key;
    }
}
