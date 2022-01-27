package Model.Statements;

import Model.Containers.*;
import Model.Heap;
import Model.PrgState;
import Values.IValue;

import java.io.BufferedReader;

public record forkStmt(IStmt stmt) implements IStmt {
//    @Override
//    public ProgramState execute(ProgramState state) throws StatementException, UndeclaredVariableException {
//        IADTStack<IStatement> new_execution_stack = new ADTStack<IStatement>();
//        return new ProgramState(new_execution_stack, state.symbolsTable().deepCopy(), state.outList(), state.fileTable(), state.heapTable(), statement);
//    }


    @Override
    public PrgState exec(PrgState state) {
        return new PrgState(new Stack<>(), state.getSymbolTable().deepCopy(), state.getPrintBuffer(), state.getFileTable(), state.getHeap(), stmt);
    }

    @Override
    public String toString() {
        return "fork(%s)".formatted(this.stmt);
    }

    public String toFileString() {
        return "fork(%s)\n".formatted(this.stmt.toFileString());
    }

    @Override
    public forkStmt deepCopy(){
        return new forkStmt(this.stmt.deepCopy());
    }
}