package Model.Expressions;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Model.Containers.IDictionary;
import Types.BoolType;
import Values.BoolValue;
import Values.IValue;

public record LogicExp(IExp first,
                       IExp second,
                       LogicOp logicOp) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) throws MissingKeyException, InvalidTypeException {
        IValue value1 = this.first.eval(symbolTable);
        if (!(value1.getType() instanceof BoolType)) {
            throw new InvalidTypeException(value1.getType().getClass(), BoolType.class);
        }

        IValue value2 = this.second.eval(symbolTable);
        if (!(value1.getType() instanceof BoolType)) {
            throw new InvalidTypeException(value2.getType().getClass(), BoolType.class);
        }

        boolean boolean1 = (boolean) value1.getValue();
        boolean boolean2 = (boolean) value2.getValue();

        return switch (this.logicOp) {
            case AND -> new BoolValue(boolean1 && boolean2);
            case OR -> new BoolValue(boolean1 || boolean2);
        };
    }

    @Override
    public LogicExp deepCopy() {
        return new LogicExp(this.first.deepCopy(), this.second.deepCopy(), this.logicOp);
    }

    @Override
    public String toString() {
        return "%s %s %s".formatted(this.first, this.logicOp.label, this.second);
    }
}

