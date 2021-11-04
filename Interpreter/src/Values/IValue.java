package Values;

import Types.IType;

public interface IValue {
    IType getType();

    Object getValue();
}