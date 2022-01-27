package Model.Statements;

import Controller.Exceptions.MyException;
import Controller.Exceptions.VariableAlreadyDeclaredException;
import Model.Containers.IDictionary;
import Model.PrgState;
import Types.IType;

public record DeclStmt(String identifier, IType type) implements IStmt {

    @Override
    public PrgState exec(PrgState state) throws VariableAlreadyDeclaredException {
        var symbolTable = state.getSymbolTable();

        if (symbolTable.has(this.identifier)) {
            throw new VariableAlreadyDeclaredException(this.identifier);
        }

        symbolTable.add(this.identifier, this.type.getDefault());

        return null;
    }

    @Override
    public String toString() {
        return "%s %s".formatted(this.type, this.identifier);
    }

    public String toFileString() {
        return "%s %s\n".formatted(this.type, this.identifier);
    }

    @Override
    public DeclStmt deepCopy(){
        return new DeclStmt(this.identifier, this.type.deepCopy());
    }

    public IDictionary<String,IType> typecheck(IDictionary<String,IType> typeEnv) throws
            MyException {
        typeEnv.add(identifier,type);
        return typeEnv;
    }
}
