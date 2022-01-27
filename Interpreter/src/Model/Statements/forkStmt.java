package Model.Statements;

import Controller.Exceptions.MyException;
import Model.Containers.*;
import Model.Heap;
import Model.PrgState;
import Types.IType;
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
    public forkStmt deepCopy() {
        return new forkStmt(this.stmt.deepCopy());
    }

    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {

        //Type type_expression = (Type) statement.typecheck(typeEnv);
        IDictionary<String, IType> copy = typeEnv.deepCopy();
        stmt.typecheck(copy);
        return typeEnv;
    }
}