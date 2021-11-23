package Values;

import Types.IType;
import Types.RefType;

public final class RefValue implements IValue {

    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {return address;}

    public final int getAndIncrement(){return address;} //returns the value before increment operation is performed to the previous value.

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(this.address, this.locationType.deepCopy());
    }

}
