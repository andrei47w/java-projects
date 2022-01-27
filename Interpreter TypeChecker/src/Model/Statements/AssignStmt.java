package Model.Statements;

import Controller.Exceptions.*;
import Model.Containers.IDictionary;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.IType;
import Values.IValue;


public record AssignStmt(String identifier,
                         IExp expression) implements IStmt {

    @Override
    public PrgState exec(PrgState state) throws MyException, ExpressionException {
        final var symbolTable = state.getSymbolTable();
        if (!symbolTable.has(this.identifier)) {
            throw new UndeclaredVariableException(this.identifier);
        }
        IValue value;
        if(expression.getClass() == HeapReadExp.class) value = this.expression.eval(symbolTable, state.getHeap());
            else value = this.expression.eval(symbolTable);
        var expectedType = symbolTable.get(this.identifier).getType();

        if (!value.getType().equals(expectedType)) {
            throw new InvalidTypeException(value.getType().getClass(), expectedType.getClass());
        }

        symbolTable.replace(this.identifier, value);

        return null;
    }

    @Override
    public String toString() {
        return "%s := %s".formatted(this.identifier, this.expression);
    }

    public String toFileString() {
        return "%s := %s\n".formatted(this.identifier, this.expression);
    }

    @Override
    public AssignStmt deepCopy(){
        return new AssignStmt(this.identifier, this.expression.deepCopy());
    }

    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException{
        IType typevar = typeEnv.get(this.identifier);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}