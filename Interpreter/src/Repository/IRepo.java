package Repository;

import Controller.Exceptions.InvalidIndexException;
import Controller.Exceptions.InvalidPathException;
import Controller.Exceptions.NoPrgStatesException;
import Model.PrgState;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IRepo {
    PrgState GetPrgState() throws NoPrgStatesException;

    void addPrgState(PrgState prgState);

    void logPrgStateExec(PrgState programState, boolean flag) throws InvalidPathException, IOException, InvalidIndexException, CloneNotSupportedException;
}

