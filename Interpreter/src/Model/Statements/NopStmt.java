package Model.Statements;

import Model.PrgState;

public record NopStmt() implements IStmt {
    @Override
    public void exec(PrgState state) {
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
}
