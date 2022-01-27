package Model.Statements;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Model.Expressions.IExp;
import Model.PrgState;

public record PrintStmt(IExp expression) implements IStmt {

    @Override
    public void exec(PrgState state) throws InvalidTypeException, MissingKeyException {
        var printBuffer = state.getPrintBuffer();

        printBuffer.append(this.expression.eval(state.getSymbolTable()));
    }

    @Override
    public String toString() {
        return "print(%s)".formatted(this.expression);
    }

    public String toFileString() {
        return "print(%s)\n".formatted(this.expression);
    }

    @Override
    public PrintStmt deepCopy() {
        return new PrintStmt(this.expression.deepCopy());
    }
}
