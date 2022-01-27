package Model.Statements;

import Controller.Exceptions.ExpressionException;
import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Controller.Exceptions.MyException;
import Model.Containers.IDictionary;
import Model.Expressions.HeapReadExp;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.BoolType;
import Types.IType;
import Values.BoolValue;
import Values.IValue;

public record IfStmt(IExp ifCondition,
                     IStmt thenStatement,
                     IStmt elseStatement) implements IStmt {


    @Override
    public PrgState exec(PrgState state) throws MyException, ExpressionException {
        var symbolTable = state.getSymbolTable();
        var executionStack = state.getExecutionStack();
        IValue conditionValue;
        if(ifCondition.getClass() == HeapReadExp.class) conditionValue = this.ifCondition.eval(state.getSymbolTable(), state.getHeap());
        else conditionValue = this.ifCondition.eval(state.getSymbolTable());

        if (!(conditionValue.getType() instanceof BoolType)) {
            throw new InvalidTypeException(BoolType.class, conditionValue.getType().getClass());
        }

        if (conditionValue.equals(new BoolValue(true))) {
            executionStack.push(this.thenStatement);
        } else {
            executionStack.push(this.elseStatement);
        }

        return null;
    }

    @Override
    public String toString() {
        return "if %s then %s else %s".formatted(this.ifCondition, this.thenStatement, this.elseStatement);
    }

    public String toFileString() {
        return "if %s then %s else %s\n".formatted(this.ifCondition, this.thenStatement, this.elseStatement);
    }

    @Override
    public IfStmt deepCopy() {
        return new IfStmt(this.ifCondition.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    public IDictionary<String, IType> typecheck(IDictionary<String,IType> typeEnv) throws
            MyException{
        IType typexp=ifCondition.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
