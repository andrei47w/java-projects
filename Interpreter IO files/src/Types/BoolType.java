package Types;

import Values.BoolValue;
import Values.IValue;

public record BoolType() implements IType {
    @Override
    public IValue getDefault() {
        return new BoolValue(false);
    }

    @Override
    public BoolType deepCopy() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
