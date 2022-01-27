package Model.Statements;

import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.PrgState;
import Types.IType;

public record CompStmt(IStmt first,
                       IStmt second) implements IStmt {

    @Override
    public PrgState exec(PrgState state) {
        final var stack = state.getExecutionStack();

        stack.push(this.second);
        stack.push(this.first);

        return null;
    }

    @Override
    public String toString() {
        return "[%s, %s]".formatted(this.first, this.second);
    }

    public String toFileString() {
        return "%s%s".formatted(this.second.toFileString(), this.first.toFileString());
    }

    @Override
    public CompStmt deepCopy(){
        return new CompStmt(this.first.deepCopy(), this.second.deepCopy());
    }

    @Override
    public IDictionary<String,IType> typecheck(IDictionary<String,IType> typeEnv) throws MyException{
        return second.typecheck(first.typecheck(typeEnv));
    }
}