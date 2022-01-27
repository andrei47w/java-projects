package Model.Statements;


import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.HeapException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.BoolType;
import Types.IType;
import Values.BoolValue;
import Values.IValue;

import java.io.IOException;

public record WhileStmt(IExp expression, IStmt statement) implements IStmt{
    @Override
    public PrgState exec(PrgState state) throws IOException, ExpressionException, MyException {
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

        return null;
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

    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {

        IType type_expression = expression.typecheck(typeEnv);
        IDictionary<String, IType> copy = typeEnv.deepCopy();

        if(type_expression.equals(new BoolType())) {
            statement.typecheck(copy);

            return typeEnv;
        }

        else {
            throw new MyException("The condition of WHILE has not the type bool");
        }
    }
}