package Model;

import Model.Containers.*;
import Model.Statements.IStmt;
import Values.IValue;

public class PrgState {
    IStack<IStmt> executionStack = new Stack<>();
    IDictionary<String, IValue> symbolTable = new Dictionary<>();
    IList<IValue> printBuffer = new List<>();
    IStmt statement;

    public PrgState(IStmt statement) {
        this.statement = statement;
        this.executionStack.push(statement);
    }

    public IStack<IStmt> getExecutionStack() {
        return this.executionStack;
    }

    public IDictionary<String, IValue> getSymbolTable() {
        return this.symbolTable;
    }

    public IList<IValue> getPrintBuffer() {
        return this.printBuffer;
    }

    @Override
    public String toString() {
        return """
                Stack:     %s
                Variables: %s
                Out:       %s
                """.formatted(this.executionStack, this.symbolTable, this.printBuffer);
    }
}