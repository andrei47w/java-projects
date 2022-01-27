package Model.Statements;


import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.HeapException;
import Controller.Exceptions.MyException;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.BoolType;
import Values.BoolValue;
import Values.IValue;

import java.io.IOException;

public record WhileStmt(IExp expression, IStmt statement) implements IStmt{
    @Override
    public void exec(PrgState state) throws IOException, ExpressionException, MyException {
        var symTable = state.getSymbolTable();
        var executionStack = state.getExecutionStack();
        IValue value;
        if(expression.getClass() == HeapReadExp.class) value = this.expression.eval(symTable, state.getHeap());
        else value = this.expression.eval(symTable);
        if(!(value.getType().equals(new BoolType())))
            throw new MyException("While statement condition not bool\n\t" + this);

        boolean condition = (boolean) value.getValue();

        if(condition) {
            executionStack.push(this);
            executionStack.push(statement);
        }
    }

    @Override
    public String toString(){
        return "while (" + expression + ")" + " do " + statement;
    }

    @Override
    public String toFileString() {
        return "while (" + expression + ")" + " do " + statement + '\n';
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(this.expression.deepCopy(), this.statement.deepCopy());
    }
}