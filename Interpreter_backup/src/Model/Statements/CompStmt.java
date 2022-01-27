package Model.Statements;

import Model.PrgState;

public record CompStmt(IStmt first,
                       IStmt second) implements IStmt {

    @Override
    public void exec(PrgState state) {
        final var stack = state.getExecutionStack();

        stack.push(this.second);
        stack.push(this.first);
    }

    @Override
    public String toString() {
        return "[%s; %s]".formatted(this.first, this.second);
    }
}