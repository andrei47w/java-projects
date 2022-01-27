package Model.Expressions;

import Model.Containers.IDictionary;
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
    public String toString() {
        return this.value.toString();
    }
}
