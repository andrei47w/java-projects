package Model;

import Controller.Exceptions.EmptyStackException;
import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.HeapException;
import Controller.Exceptions.MyException;
import Model.Containers.*;
import Model.Statements.IStmt;
import Values.IValue;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    IStack<IStmt> executionStack = new Stack<>();
    IDictionary<String, IValue> symbolTable = new Dictionary<>();
    IList<IValue> printBuffer = new MyList<>();
    IDictionary<String, BufferedReader> fileTable = new Dictionary<>();
    Heap heap = new Heap();
    IStmt statement;
    private int stateID;
    public static int lastID = 0;

    public PrgState(IStmt statement) {
        this.statement = statement;
        this.executionStack.push(statement);
    }

    public synchronized int getNewPrgStateID() {
        ++lastID;
        return lastID;
    }

    public PrgState(IStack<IStmt> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> printBuffer, IDictionary<String, BufferedReader> fileTable, Heap heap, IStmt statement) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.printBuffer = printBuffer;
        this.fileTable = fileTable;
        this.statement = statement;
        this.heap = heap;
        this.stateID = this.getNewPrgStateID();
    }

    public String getIDtoString(){
        return String.valueOf(this.stateID);
    }

    public Boolean isNotCompleted() {
        if(this.executionStack.isEmpty()) return false;
        return true;
    }

    public PrgState oneStep() throws MyException, IOException, HeapException, ExpressionException {
        if (this.executionStack.isEmpty()) {
            throw new EmptyStackException();
        }

        return this.executionStack.pop().exec(this);
    }

    public IStack<IStmt> getExecutionStack() {
        return this.executionStack;
    }

    public Heap getHeap() {
        return this.heap;
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
                ID:        %s
                Stack:     %s
                Variables: %s
                Out:       %s
                Files:     %s
                Heap:      %s
                """.formatted(this.stateID, this.executionStack, this.symbolTable, this.printBuffer, this.fileTable, this.heap);
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
        return new PrgState(this.executionStack.deepCopy(), this.symbolTable.deepCopy(), this.printBuffer.deepCopy(), this.fileTable.deepCopy(), this.heap.deepCopy(), this.statement.deepCopy());
    }

    public int getId() {
        return this.stateID;
    }
}