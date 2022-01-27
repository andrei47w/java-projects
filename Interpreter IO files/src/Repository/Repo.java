package Repository;

import Controller.Exceptions.InvalidIndexException;
import Controller.Exceptions.InvalidPathException;
import Controller.Exceptions.NoPrgStatesException;
import Model.Containers.IStack;
import Model.Containers.List;
import Model.PrgState;
import Model.Statements.IStmt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Scanner;

public class Repo implements IRepo {
    private final List<PrgState> programStateList = new List<>();
    private String logFilePath;

    public Repo(String logFilePath) {
        this.logFilePath = logFilePath;
    }

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

    private List<String> ReadFile() throws InvalidPathException, FileNotFoundException {
        if (this.logFilePath.isEmpty()) throw new InvalidPathException();

        File file = new File(this.logFilePath);
        Scanner reader = new Scanner(file);

        List<String> data = new List<>();
        while (reader.hasNextLine()) {
            data.append(reader.nextLine());
        }
        reader.close();

        return data;
    }

    @Override
    public void logPrgStateExec(PrgState programState, boolean flag) throws InvalidPathException, IOException {
        if (!Files.exists(Path.of(this.logFilePath).getParent())) throw new InvalidPathException();
        if (flag) new File(this.logFilePath).delete();

        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));

        logFile.write("ExeStack:\n");
        IStack<IStmt> stack = programState.getExecutionStack();
        Iterator<IStmt> itr = stack.iterator();
        while (itr.hasNext()) {
            String str = itr.next().toFileString();
            str = str.substring(0, str.length() - 1);
            logFile.write("[" + str + "]\n");
        }
        logFile.write("SymTable:\n" + programState.getSymbolTable().toFileString());
        logFile.write("Out:\n" + programState.getPrintBuffer().toFileString());
        logFile.write("FileTable:\n" + programState.getFileTable().toFileString());
        logFile.write("\n--------------------------------------\n");
        logFile.close();
    }
}
