package Model.Statements;

import Model.Containers.IDictionary;
import Model.PrgState;
import Types.IType;

public record NopStmt() implements IStmt {
    @Override
    public PrgState exec(PrgState state) {
        return null;
    }

    @Override
    public String toFileString() {
        return null;
    }

    @Override
    public NopStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return null;
    }

    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) {
        return typeEnv;
    }
}
