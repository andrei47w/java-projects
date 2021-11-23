package Model.Statements;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.MyException;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.PrgState;
import Values.IValue;

public record PrintStmt(IExp expression) implements IStmt {

    @Override
    public void exec(PrgState state) throws MyException, ExpressionException {
        var printBuffer = state.getPrintBuffer();

        IValue string;
        if(expression.getClass() == HeapReadExp.class) string = this.expression.eval(state.getSymbolTable(), state.getHeap());
        else string = this.expression.eval(state.getSymbolTable());
        printBuffer.append(string);
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
