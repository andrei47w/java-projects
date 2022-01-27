package Model.Expressions;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Heap;
import Values.IValue;

public record ValueExp(IValue value) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) {
        return this.value;
    }

    @Override
    public ValueExp deepCopy() {
        return new ValueExp(this.value);
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbols, Heap heap) throws ExpressionException, MyException {
        return null;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
