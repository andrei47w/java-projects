package Model.Expressions;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Heap;
import Types.BoolType;
import Types.IType;
import Types.IntType;
import Values.BoolValue;
import Values.IValue;
import Values.IntValue;

public record RelExp(RelOp relOp, IExp first,
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

        return switch (this.relOp) {
            case LOWER -> new BoolValue(number1 < number2);
            case LOWERE -> new BoolValue(number1 <= number2);
            case EQUAL -> new BoolValue(number1 == number2);
            case NOTEQUAL -> new BoolValue(number1 != number2);
            case HIGHER -> new BoolValue(number1 > number2);
            case HIGHERE -> new BoolValue(number1 >= number2);
        };
    }

    @Override
    public RelExp deepCopy() {
        return new RelExp(this.relOp, this.first.deepCopy(), this.second.deepCopy());
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = first.typecheck(typeEnv);
        type2 = second.typecheck(typeEnv);

        if(type1.equals(new IntType())) {
            if(type2.equals(new IntType())) {
                return new BoolType();
            }

            else {
                throw new MyException("Second operand is not an integer");
            }
        }

        else {
            throw new MyException("First operand is not an integer");
        }
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbols, Heap heap) throws ExpressionException, MyException {
        return null;
    }

    @Override
    public String toString() {
        return "%s %s %s".formatted(this.first, this.relOp.label, this.second);
    }
}