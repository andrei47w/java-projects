package Model.Statements;


import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.HeapException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.Heap;
import Model.PrgState;
import Types.IType;
import Types.RefType;
import Values.IValue;
import Values.RefValue;

import java.io.FileNotFoundException;
import java.io.IOException;

public record HeapWriteStmt(String variableName, IExp expression) implements IStmt {
    @Override
    public PrgState exec(PrgState state) throws IOException, FileNotFoundException, HeapException, MyException, ExpressionException {
        IDictionary<String, IValue> symTable = state.getSymbolTable();
        Heap heap = state.getHeap();

        IValue newValue;
        if (expression.getClass() == HeapReadExp.class) newValue = this.expression.eval(symTable, heap);
        else newValue = this.expression.eval(state.getSymbolTable());
        if (!symTable.has(variableName))
            throw new HeapException("Heap write attempt to read address from undeclared variable\n\t" + this);

        if (!(symTable.get(variableName) instanceof RefValue))
            throw new HeapException("Heap write attempt to read address from non-RefValue value\n\t" + this);

        int address = ((RefValue) symTable.get(variableName)).getAddress();
        if (!heap.has(address))
            throw new HeapException("Heap write attempt to write into unexisting heap address\n\t" + this);

        if (!((RefType) (symTable.get(variableName).getType())).getInner().equals(newValue.getType()))
            throw new HeapException("Heap write attempt with non compatible value type\n\t" + this);

        heap.replace(address, newValue);

        return null;
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
    public String toString() {
        return "writeH(" + variableName + ", " + expression + ")";
    }

    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {

        IType type_var = typeEnv.get(variableName);
        IType type_expression = expression.typecheck(typeEnv);

        if (type_var.equals(new RefType(type_expression))) {
            return typeEnv;
        } else {
            throw new MyException("write heap statement: not reference type");
        }
    }
}

