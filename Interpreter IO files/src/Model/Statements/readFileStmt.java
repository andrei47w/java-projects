package Model.Statements;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.UndeclaredVariableException;
import Controller.Exceptions.VariableAlreadyDeclaredException;
import Model.Containers.IDictionary;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.IntType;
import Types.StringType;
import Values.IValue;
import Values.IntValue;

import java.io.BufferedReader;
import java.io.IOException;

public record readFileStmt(IExp expression, String var_name) implements IStmt{
    @Override
    public void exec(PrgState state) throws InvalidTypeException, UndeclaredVariableException, MissingKeyException, VariableAlreadyDeclaredException, IOException {
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        final var symbolTable = state.getSymbolTable();
        final var string = this.expression.eval(state.getSymbolTable());
        if (!symbolTable.has(this.var_name)) {
            throw new UndeclaredVariableException(this.var_name);
        }

        if (!string.getType().equals(new StringType())) {
            throw new InvalidTypeException(string.getType().getClass(), StringType.class);
        }

        if (!fileTable.has((String) string.getValue())) {
            throw new UndeclaredVariableException(string.toString());
        }

        final var reader = fileTable.get((String) string.getValue());
        String line = reader.readLine();
        int intValue = 0;
        if (line != null) {
            intValue = Integer.parseInt(line);
        }

        symbolTable.replace(this.var_name, new IntValue(intValue));
    }

    @Override
    public String toString() {
        return "readFile(%s, %s)".formatted(this.expression, this.var_name);
    }

    public String toFileString() {
        return "readFile(%s, %s)\n".formatted(this.expression, this.var_name);
    }

    @Override
    public readFileStmt deepCopy() {
        return new readFileStmt(this.expression.deepCopy(), this.var_name);
    }
}