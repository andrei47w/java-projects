package Model.Statements;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.UndeclaredVariableException;
import Controller.Exceptions.VariableAlreadyDeclaredException;
import Model.Containers.IDictionary;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.StringType;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFile(IExp expression) implements IStmt{
    @Override
    public void exec(PrgState state) throws InvalidTypeException, UndeclaredVariableException, MissingKeyException, VariableAlreadyDeclaredException, IOException {
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        final var string = this.expression.eval(state.getSymbolTable());

        if (!string.getType().equals(new StringType())) {
            throw new InvalidTypeException(string.getType().getClass(), StringType.class);
        }

        if (!fileTable.has((String) string.getValue())) {
            throw new VariableAlreadyDeclaredException(string.toString());
        }

        fileTable.get((String) string.getValue()).close();
        fileTable.remove((String) string.getValue());
    }

    @Override
    public String toString() {
        return "closeRFile(%s)".formatted(this.expression);
    }

    public String toFileString() {
        return "closeRFile(%s)\n".formatted(this.expression);
    }

    @Override
    public CloseRFile deepCopy(){
        return new CloseRFile(this.expression.deepCopy());
    }
}