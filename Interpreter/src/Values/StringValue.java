package Values;

import Types.IType;
import Types.StringType;

import java.util.Objects;

public record StringValue(String value) implements IValue {


    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public StringValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringValue stringValue)) return false;

        return Objects.equals(value, stringValue.value);
    }
}