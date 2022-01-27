package Model.Statements;

import Controller.Exceptions.InvalidTypeException;
import Controller.Exceptions.MissingKeyException;
import Model.Expressions.IExp;
import Model.PrgState;
import Types.BoolType;
import Values.BoolValue;

public record IfStmt(IExp ifCondition,
                     IStmt thenStatement,
                     IStmt elseStatement) implements IStmt {


    @Override
    public void exec(PrgState state) throws InvalidTypeException, MissingKeyException {
        var symbolTable = state.getSymbolTable();
        var executionStack = state.getExecutionStack();
        var conditionValue = this.ifCondition.eval(symbolTable);

        if (!(conditionValue.getType() instanceof BoolType)) {
            throw new InvalidTypeException(BoolType.class, conditionValue.getType().getClass());
        }

        if (conditionValue.equals(new BoolValue(true))) {
            executionStack.push(this.thenStatement);
        } else {
            executionStack.push(this.elseStatement);
        }
    }

    @Override
    public String toString() {
        return "if %s then %s else %s".formatted(this.ifCondition, this.thenStatement, this.elseStatement);
    }
}
