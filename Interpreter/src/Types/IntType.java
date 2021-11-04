package Types;

import Values.IValue;
import Values.IntValue;

public record IntType() implements IType {
    @Override
    public IValue getDefault() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
