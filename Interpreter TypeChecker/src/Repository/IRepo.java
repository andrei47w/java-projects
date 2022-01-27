package Repository;

import Controller.Exceptions.InvalidIndexException;
import Controller.Exceptions.InvalidPathException;
import Controller.Exceptions.NoPrgStatesException;
import Model.Containers.MyList;
import Model.PrgState;

import java.io.IOException;

public interface IRepo {
    MyList<PrgState> getPrgList() throws NoPrgStatesException;

    void setPrgList(MyList<PrgState> new_prgStateMyList);

//    PrgState GetPrgState() throws NoPrgStatesException;

    void addPrgState(PrgState prgState);

    void logPrgStateExec(PrgState programState, boolean flag) throws InvalidPathException, IOException, InvalidIndexException, CloneNotSupportedException;
}

