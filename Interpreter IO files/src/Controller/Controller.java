package Controller;

import Controller.Exceptions.*;
import Model.PrgState;
import Repository.IRepo;
import Repository.Repo;

import java.io.IOException;

public class Controller {
    private IRepo repo;

    public Controller(Repo repo) {
        this.repo = repo;
    }

    public void addProgramState(PrgState prgState) {
        this.repo.addPrgState(prgState);
    }

    public void oneStep(PrgState state) throws EmptyStackException, VariableAlreadyDeclaredException, InvalidTypeException, UndeclaredVariableException, MissingKeyException, IOException {
        final var stack = state.getExecutionStack();
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }

        stack.pop().exec(state);
    }

    public PrgState allStep() throws NoPrgStatesException, EmptyStackException, VariableAlreadyDeclaredException, InvalidTypeException, UndeclaredVariableException, MissingKeyException, InvalidIndexException, IOException, InvalidPathException, CloneNotSupportedException {
        final var programState = this.repo.GetPrgState();
        this.repo.logPrgStateExec(programState, true);

        while (!programState.getExecutionStack().isEmpty()) {
            System.out.println(programState);
            this.oneStep(programState);
            this.repo.logPrgStateExec(programState, false);
        }

        return programState;
    }

}
