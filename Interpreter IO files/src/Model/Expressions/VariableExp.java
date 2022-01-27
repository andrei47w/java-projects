package Model.Expressions;

import Model.Containers.IDictionary;
import Values.IValue;

public record VariableExp(String key) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) {
        return symbolTable.get(this.key);
    }

    @Override
    public VariableExp deepCopy() {
        return new VariableExp(this.key);
    }

    @Override
    public String toString() {
        return this.key;
    }
}
