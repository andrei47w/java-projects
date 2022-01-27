package Types;

import Values.IValue;
import Values.StringValue;


public record StringType() implements IType {
    @Override
    public IValue getDefault() {
        return new StringValue("");
    }

    @Override
    public StringType deepCopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public String toString() {
        return "str";
    }
}