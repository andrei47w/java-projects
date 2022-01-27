package Model.Statements;

import Controller.Exceptions.*;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.PrgState;
import Values.IValue;


public record AssignStmt(String identifier,
                         IExp expression) implements IStmt {

    @Override
    public void exec(PrgState state) throws MyException, ExpressionException {
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
}