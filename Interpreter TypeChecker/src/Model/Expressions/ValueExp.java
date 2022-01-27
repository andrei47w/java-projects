package Model.Expressions;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Heap;
import Types.IType;
import Values.IValue;

import java.lang.reflect.Type;

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
    public IType typecheck(IDictionary<String,IType> typeEnv) throws MyException{
        return value.getType();
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
