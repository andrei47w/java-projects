package Model.Statements;


import Controller.Exceptions.*;
import Model.Containers.IDictionary;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.Heap;
import Model.PrgState;
import Types.IType;
import Types.RefType;
import Values.IValue;
import Values.RefValue;

import java.io.IOException;

public record HeapAllocStmt(String varName, IExp expression) implements IStmt {
    @Override
    public PrgState exec(PrgState state) throws IOException, MyException, HeapException, ExpressionException {
        IDictionary<String, IValue> symTable = state.getSymbolTable();
        Heap heap = state.getHeap();
        IValue value;
        if(expression.getClass() == HeapReadExp.class) value = this.expression.eval(state.getSymbolTable(), state.getHeap());
        else value = this.expression.eval(state.getSymbolTable());


        if(symTable.has(varName))
            if(symTable.get(varName).getType() instanceof RefType)
                if(((RefType)symTable.get(varName).getType()).getInner().equals(value.getType())){

                    int address = heap.add(value);
                    symTable.replace(varName, new RefValue(address, value.getType()));
                }
                else
                    throw new HeapException("Allocation attempt with value of invalid type\n\t" + this);
            else
                throw new HeapException("Allocation attempt for non RefType variable\n\t" + this);
        else
            throw new HeapException("Allocation attempt for undeclared variable\n\t" + this);

        return null;
    }

    @Override
    public String toFileString() {
        return "new(" + varName +", " + expression + ")\n";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapAllocStmt(this.varName, this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return "new(" + varName +", " + expression + ")";
    }

    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {

        IType type_var = typeEnv.get(varName);
        IType type_expression = expression.typecheck(typeEnv);

        if (type_var.equals(new RefType(type_expression))) {
            return typeEnv;
        } else {
            throw new MyException("write heap statement: not reference type");
        }
    }
}

