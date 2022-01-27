package Model;

import Model.Containers.*;
import Model.Statements.IStmt;
import Values.IValue;

import java.io.BufferedReader;

public class PrgState {
    IStack<IStmt> executionStack = new Stack<>();
    IDictionary<String, IValue> symbolTable = new Dictionary<>();
    IList<IValue> printBuffer = new List<>();
    IDictionary<String, BufferedReader> fileTable = new Dictionary<>();
    IStmt statement;

    public PrgState(IStmt statement) {
        this.statement = statement;
        this.executionStack.push(statement);
    }

    public PrgState(IStack<IStmt> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> printBuffer, IDictionary<String, BufferedReader> fileTable, IStmt statement) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.printBuffer = printBuffer;
        this.fileTable = fileTable;
        this.statement = statement;
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

    public IDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    @Override
    public String toString() {
        return """
                Stack:     %s
                Variables: %s
                Out:       %s
                Files:     %s
                """.formatted(this.executionStack, this.symbolTable, this.printBuffer, this.fileTable);
    }

    public String exeToString() {
        var stack = this.executionStack;
        String str = "";
        while (!stack.isEmpty()) {
            str += stack.pop().toString() + '\n';
        }
        return str;
    }

    public PrgState deepCopy() {
        return new PrgState(this.executionStack.deepCopy(), this.symbolTable.deepCopy(), this.printBuffer.deepCopy(), this.fileTable.deepCopy(), this.statement.deepCopy());
    }
}