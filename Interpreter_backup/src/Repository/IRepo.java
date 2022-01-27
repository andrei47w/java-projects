package Repository;

import Controller.Exceptions.NoPrgStatesException;
import Model.PrgState;

public interface IRepo {
    PrgState GetPrgState() throws NoPrgStatesException;

    void addPrgState(PrgState prgState);
}

