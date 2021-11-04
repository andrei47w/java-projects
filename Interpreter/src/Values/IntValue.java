package Values;

import Types.IType;
import Types.IntType;

public record IntValue(int value) implements IValue {


    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValue intValue)) return false;

        return value == intValue.value;
    }
}
