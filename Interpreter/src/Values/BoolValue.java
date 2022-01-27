package Values;

import Types.BoolType;
import Types.IType;

public record BoolValue(boolean value) implements IValue {


    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public BoolValue deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public String toString() {
        if (this.value) return "true";
        return "false";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoolValue boolValue)) return false;

        return value == boolValue.value;
    }


}
