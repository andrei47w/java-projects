package Repository;

import Controller.Exceptions.InvalidIndexException;
import Controller.Exceptions.NoPrgStatesException;
import Model.Containers.List;
import Model.PrgState;

public class Repo implements IRepo {
    private final List<PrgState> programStateList = new List<>();

    @Override
    public PrgState GetPrgState() throws NoPrgStatesException {
        try {
            return this.programStateList.removeAt(0);
        } catch (InvalidIndexException exception) {
            throw new NoPrgStatesException();
        }
    }

    @Override
    public void addPrgState(PrgState prgState) {
        this.programStateList.append(prgState);
    }
}
