package Controller;

import Controller.Exceptions.*;
import Model.PrgState;
import Repository.IRepo;
import Repository.Repo;

public class Controller {
    private final IRepo repo = new Repo();


    public void addProgramState(PrgState prgState) {
        this.repo.addPrgState(prgState);
    }

    public void oneStep(PrgState state) throws EmptyStackException, VariableAlreadyDeclaredException, InvalidTypeException, UndeclaredVariableException, MissingKeyException {
        final var stack = state.getExecutionStack();
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }

        stack.pop().exec(state);
    }

    public PrgState allStep() throws NoPrgStatesException, EmptyStackException, VariableAlreadyDeclaredException, InvalidTypeException, UndeclaredVariableException, MissingKeyException {
        final var programState = this.repo.GetPrgState();
        while (!programState.getExecutionStack().isEmpty()) {
            System.out.println(programState);
            this.oneStep(programState);
        }

        return programState;
    }

}
