package Controller;

import Controller.Exceptions.*;
import Model.PrgState;
import Repository.IRepo;
import Repository.Repo;
import Values.IValue;
import Values.RefValue;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private IRepo repo;

    public Controller(Repo repo) {
        this.repo = repo;
    }

    public void addProgramState(PrgState prgState) {
        this.repo.addPrgState(prgState);
    }

    public void oneStep(PrgState state) throws MyException, IOException, HeapException, ExpressionException {
        final var stack = state.getExecutionStack();
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }

        stack.pop().exec(state);
    }

    public PrgState allStep() throws MyException, IOException, CloneNotSupportedException, HeapException, ExpressionException {
        final var programState = this.repo.GetPrgState();
        this.repo.logPrgStateExec(programState, true);

        while (!programState.getExecutionStack().isEmpty()) {
            System.out.println(programState);
            this.oneStep(programState);

            programState.getHeap().Map = new HashMap<Integer, IValue>(garbage_collector(get_used_addresses(programState.getSymbolTable().values(),
                    programState.getHeap().values()), programState.getHeap().Map));
            this.repo.logPrgStateExec(programState, false);
        }

        return programState;
    }

    private Map<Integer, IValue> garbage_collector(List<Integer> used_addresses, Map<Integer, IValue> heap)
    {
        return heap.entrySet().stream()
                .filter(e -> used_addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> get_used_addresses(Collection<IValue> symbols_table_values, Collection<IValue> heap_table_values)
    {
        List<Integer> symbols_table_addresses = symbols_table_values.stream()
                .filter(v -> v instanceof RefValue)
                .map(value->{RefValue value2 = (RefValue) value;
                    return value2.getAddress();})
                .collect(Collectors.toList());

        List<Integer> heap_table_addresses = heap_table_values.stream()
                .filter(v -> v instanceof RefValue)
                .map(value->{RefValue value2 = (RefValue) value;
                    return value2.getAddress();})
                .collect(Collectors.toList());

        symbols_table_addresses.addAll(heap_table_addresses);
        return symbols_table_addresses;
    }
}
