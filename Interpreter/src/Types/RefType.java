package Types;


import Values.IValue;
import Values.RefValue;

import java.util.Objects;

public final class RefType implements IType {

    private final IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType) {
            return ((RefType) obj).getInner().equals(this.inner);
        }

        return false;
    }

//    public static RefType (IType t, IType inner) {
//        if (!(t instanceof ReferenceType)) {
//            throw new TypeMismatch(t, new ReferenceType(inner));
//        }
//
//        ReferenceType refT = (ReferenceType) t;
//        if (!Objects.equals(refT.getInner(), inner)) {
//            throw new TypeMismatch(refT.getInner(), inner);
//        }
//
//        return refT;
//    }

    public IType getInner() {
        return inner;
    }

    @Override
    public IValue getDefault() {
        return new RefValue(0, inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner.deepCopy());
    }

    @Override
    public String toString() {
        return "Ref(" + inner + ")";
    }
}