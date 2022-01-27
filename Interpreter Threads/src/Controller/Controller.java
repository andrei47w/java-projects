package Controller;

import Controller.Exceptions.*;
import Model.Containers.IDictionary;
import Model.Containers.MyList;
import Model.Heap;
import Model.PrgState;
import Repository.IRepo;
import Repository.Repo;
import Values.IValue;
import Values.RefValue;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Controller {
    private final IRepo repo;
    private ExecutorService executor;
    private final GarbageCollector garbageCollector;

    public Controller(Repo repo) {
        this.repo = repo;
        this.garbageCollector = new GarbageCollector();
    }

    public void addProgramState(PrgState prgState) {
        this.repo.addPrgState(prgState);
    }

//    public void oneStep(PrgState state) throws MyException, IOException, HeapException, ExpressionException {
//        final var stack = state.getExecutionStack();
//        if (stack.isEmpty()) {
//            throw new EmptyStackException();
//        }
//
//        stack.pop().exec(state);
//    }

    public MyList<PrgState> removeCompletedProgram(MyList<PrgState> inProgress){
        MyList<PrgState> newMyList = new MyList<>();

        for(PrgState state : inProgress) {
            if (state.isNotCompleted()) newMyList.append(state);
        }

        return newMyList;
    }

//    public PrgState allStep() throws MyException, IOException, CloneNotSupportedException, HeapException, ExpressionException {
//        final var programState = this.repo.GetPrgState();
//        this.repo.logPrgStateExec(programState, true);
//
//        while (!programState.getExecutionStack().isEmpty()) {
//            System.out.println(programState);
//            this.oneStep(programState);
//
//            programState.getHeap().Map = new HashMap<Integer, IValue>(garbage_collector(get_used_addresses(programState.getSymbolTable().values(),
//                    programState.getHeap().values()), programState.getHeap().Map));
//            this.repo.logPrgStateExec(programState, false);
//        }
//
//        return programState;
//    }

    public MyList<PrgState> oneStepForAllPrograms(MyList<PrgState> programs) throws InvalidIndexException, IOException, InvalidPathException, CloneNotSupportedException {
        executor = Executors.newFixedThreadPool(2);
        //prepare list of callables
        List<Callable<PrgState>> callList = programs.stream()
                .map((PrgState program) -> (Callable<PrgState>) program::oneStep)
                .collect(Collectors.toList());

        //start execution of callables
        List<PrgState> newProgramList = null;
        try {
            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            System.out.println("One step failed: " + Arrays.toString(e.getStackTrace()));
                        }
                        return null;
                    })
                    .filter(Objects::nonNull) // keeps only not null values
                    .collect(Collectors.toList());
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }

        //add new threads to existing ones
        programs.addAll(newProgramList);

//        repo.logPrgStateExec(null, true);
        programs.forEach(program -> {
            try {
                repo.logPrgStateExec(program, false);
                //System.out.println(program.toString() + '\n');
            } catch (MyException | IOException | CloneNotSupportedException e) {
                System.out.println("One step failed: " + Arrays.toString(e.getStackTrace()));
            }
        });
        executor.shutdownNow();
        repo.setPrgList(programs);

        return programs;
    }


    public MyList<PrgState> allStep() throws NoPrgStatesException, InvalidIndexException, IOException, InvalidPathException, CloneNotSupportedException {
        executor = Executors.newFixedThreadPool(2);
        MyList<PrgState> programState = null;

                MyList<PrgState> programMyList = removeCompletedProgram(repo.getPrgList());
        while(programMyList.size() > 0){
            repo.getPrgList().stream().forEach(program -> {
                try {
                    program.getHeap().setContent(
                            garbageCollector.addIndirections(garbageCollector.getAddressFromTables(repo.getPrgList()),program.getHeap()),
                            program.getHeap());
                } catch (NoPrgStatesException e) {
                    e.printStackTrace();
                }
            });
//                    this.garbage_collector(this.get_used_addresses(program.getSymbolTable(), program.getHeap()), program.getHeap().Map)));

            programState = oneStepForAllPrograms(programMyList);
            programMyList = removeCompletedProgram(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(programMyList);
//        repo.reset();
        return programState;
    }
}
