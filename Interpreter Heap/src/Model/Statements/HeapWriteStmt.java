package Model.Statements;




import Controller.Exceptions.*;
import Model.Containers.IDictionary;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.Heap;
import Model.PrgState;
import Types.RefType;
import Values.IValue;
import Values.RefValue;

import java.io.FileNotFoundException;
import java.io.IOException;

public record HeapWriteStmt(String variableName , IExp expression) implements IStmt {
    @Override
    public void exec(PrgState state) throws IOException, FileNotFoundException, HeapException, MyException, ExpressionException {
        IDictionary<String, IValue> symTable = state.getSymbolTable();
        Heap heap = state.getHeap();

        IValue newValue;
        if(expression.getClass() == HeapReadExp.class) newValue = this.expression.eval(state.getSymbolTable(), state.getHeap());
        else newValue = this.expression.eval(state.getSymbolTable());
        if(!symTable.has(variableName))
            throw  new HeapException("Heap write attempt to read address from undeclared variable\n\t" + this);

        if(!(symTable.get(variableName) instanceof RefValue))
            throw new HeapException("Heap write attempt to read address from non-RefValue value\n\t" + this);

        int address = ((RefValue) symTable.get(variableName)).getAddress();
        if(!heap.has(address))
            throw new HeapException("Heap write attempt to write into unexisting heap address\n\t" + this);

        if(!((RefType)(symTable.get(variableName).getType())).getInner().equals(newValue.getType()))
            throw new HeapException("Heap write attempt with non compatible value type\n\t" + this);

        heap.replace(address, newValue);
    }

    @Override
    public String toFileString() {
        return "writeH(" + variableName + ", " + expression + ")\n";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriteStmt(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return "writeH(" + variableName + ", " + expression + ")";
    }
}

