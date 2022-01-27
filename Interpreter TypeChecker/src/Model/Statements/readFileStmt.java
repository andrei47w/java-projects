package Model.Statements;

import Controller.Exceptions.*;
import Model.Containers.IDictionary;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.IType;
import Types.IntType;
import Types.StringType;
import Values.IValue;
import Values.IntValue;

import java.io.BufferedReader;
import java.io.IOException;

public record readFileStmt(IExp expression, String var_name) implements IStmt{
    @Override
    public PrgState exec(PrgState state) throws MyException, IOException, ExpressionException {
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        final var symbolTable = state.getSymbolTable();
        IValue string;
        if(expression.getClass() == HeapReadExp.class) string = this.expression.eval(state.getSymbolTable(), state.getHeap());
        else string = this.expression.eval(state.getSymbolTable());
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

        return null;
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

    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {

        IType type_expression = expression.typecheck(typeEnv);

        if (type_expression.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("open/read/close file statement: not a string type");
        }
    }
}