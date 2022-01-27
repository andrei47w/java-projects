package Model.Statements;

import Controller.Exceptions.VariableAlreadyDeclaredException;
import Model.PrgState;
import Types.IType;

public record DeclStmt(String identifier, IType type) implements IStmt {

    @Override
    public void exec(PrgState state) throws VariableAlreadyDeclaredException {
        var symbolTable = state.getSymbolTable();

        if (symbolTable.has(this.identifier)) {
            throw new VariableAlreadyDeclaredException(this.identifier);
        }

        symbolTable.add(this.identifier, this.type.getDefault());
    }

    @Override
    public String toString() {
        return "%s %s".formatted(this.type, this.identifier);
    }
}
