package Model.Expressions;


import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Heap;
import Types.IType;
import Types.RefType;
import Values.IValue;
import Values.RefValue;

public record HeapReadExp(IExp expression) implements IExp {

    public IValue eval(IDictionary<String, IValue> symbols, Heap heap) throws ExpressionException, MyException {
        IValue value;
        if(expression.getClass() == HeapReadExp.class) value = expression.eval(symbols, heap);
            else value = expression.eval(symbols);
        if(!(value instanceof RefValue))
            throw new ExpressionException("Heap read attempt on non-RefValue\n\t" + this);
        int address = ((RefValue) value).getAddress();
        if(heap.has(address))
            try {
                return heap.get(address);
            } catch (Exception e){
                throw new ExpressionException("Dictionary error");
            }
        else
            throw new ExpressionException("Heap read attempt on unexisting address\n\t" + this);
    }

    @Override
    public String toString(){
        return "heapRead " + expression;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) throws MyException, ExpressionException {
        return null;
    }

    @Override
    public IType typecheck(IDictionary<String,IType> typeEnv) throws MyException{
        IType typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public IExp deepCopy() {
        return new HeapReadExp(this.expression.deepCopy());
    }
}

