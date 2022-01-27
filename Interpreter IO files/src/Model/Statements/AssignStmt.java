package Model.Statements;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.UndeclaredVariableException;
import Model.Expressions.IExp;
import Model.PrgState;


public record AssignStmt(String identifier,
                         IExp expression) implements IStmt {

    @Override
    public void exec(PrgState state) throws InvalidTypeException, UndeclaredVariableException, MissingKeyException {
        final var symbolTable = state.getSymbolTable();
        if (!symbolTable.has(this.identifier)) {
            throw new UndeclaredVariableException(this.identifier);
        }

        var value = this.expression.eval(symbolTable);
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