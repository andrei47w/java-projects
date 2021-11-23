package Model.Expressions;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Heap;
import Types.IntType;
import Values.IValue;
import Values.IntValue;

public record ArithExp(ArithOp arithOp,
                       IExp first,
                       IExp second) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) throws MyException, ExpressionException {
        IValue value1 = this.first.eval(symbolTable);
        if (!(value1.getType() instanceof IntType)) {
            throw new InvalidTypeException(value1.getType().getClass(), IntType.class);
        }

        IValue value2 = this.second.eval(symbolTable);
        if (!(value1.getType() instanceof IntType)) {
            throw new InvalidTypeException(value2.getType().getClass(), IntType.class);
        }

        int number1 = (int) value1.getValue();
        int number2 = (int) value2.getValue();

        return switch (this.arithOp) {
            case ADDITION -> new IntValue(number1 + number2);
            case SUBTRACTION -> new IntValue(number1 - number2);
            case MULTIPLICATION -> new IntValue(number1 * number2);
            case DIVISION -> new IntValue(number1 / number2);
        };
    }

    @Override
    public ArithExp deepCopy() {
        return new ArithExp(this.arithOp, this.first.deepCopy(), this.second.deepCopy());
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbols, Heap heap) throws ExpressionException, MyException {
        return null;
    }

    @Override
    public String toString() {
        return "%s %s %s".formatted(this.first, this.arithOp.label, this.second);
    }
}
