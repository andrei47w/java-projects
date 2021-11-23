import Controller.Controller;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Repository.Repo;
import Types.IntType;
import Types.RefType;
import Types.StringType;
import Values.IntValue;
import Values.StringValue;
import View.Commands.ExitCommand;
import View.Commands.RunExample;
import View.TextMenu;

public class Main {
    public static void CommandRun() {
        PrgState prg1 = new PrgState(new CompStmt(new DeclStmt(
                "v",
                new IntType()),
                new CompStmt(new AssignStmt(
                        "v",
                        new ValueExp(new IntValue(2))),
                        new PrintStmt(new VariableExp("v")))));
        Controller ctr1 = new Controller(new Repo("logs/log1.txt"));
        ctr1.addProgramState(prg1);

        PrgState prg2 = new PrgState(new CompStmt(new DeclStmt(
                "varf",
                new StringType()),
                new CompStmt(new AssignStmt(
                        "varf",
                        new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VariableExp("varf")),
                                new CompStmt(new DeclStmt(
                                        "varc",
                                        new IntType()),
                                        new CompStmt(new readFileStmt(
                                                new VariableExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                        new CompStmt(new readFileStmt(
                                                                new VariableExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                                        new CloseRFile(new VariableExp("varf")))))))))));
        Controller ctr2 = new Controller(new Repo("logs/log2.txt"));
        ctr2.addProgramState(prg2);

        PrgState prg3 = new PrgState(new IfStmt(
                new RelExp(RelOp.HIGHER, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(5))),
                new PrintStmt(new ValueExp(new IntValue(5))),
                new PrintStmt(new ValueExp(new IntValue(2)))));
        Controller ctr3 = new Controller(new Repo("logs/log3.txt"));
        ctr3.addProgramState(prg3);

        PrgState prg4 = new PrgState(new CompStmt(
                new DeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new DeclStmt("a",
                                        new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HeapAllocStmt("a", new VariableExp("v")),
                                        new CompStmt(
                                                new HeapAllocStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(
                                                        new HeapReadExp(
                                                                new HeapReadExp(
                                                                        new VariableExp("a"))))))))));
        Controller ctr4 = new Controller(new Repo("logs/log4.txt"));
        ctr4.addProgramState(prg4);

        PrgState prg5 = new PrgState(new CompStmt(
                new DeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelExp(RelOp.HIGHER,
                                                new VariableExp("v"),
                                                new ValueExp(new IntValue(0))

                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VariableExp("v")),
                                                new AssignStmt(
                                                        "v",
                                                        new ArithExp(ArithOp.SUBTRACTION,
                                                                new VariableExp("v"),
                                                                new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VariableExp("v"))))));
        Controller ctr5 = new Controller(new Repo("logs/log5.txt"));
        ctr5.addProgramState(prg5);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit\n"));
//        menu.addCommand(new RunExample("1", prg1.toString(), ctr1));
//        menu.addCommand(new RunExample("2", prg2.toString(), ctr2));
        menu.addCommand(new RunExample("3", prg3.toString(), ctr3));
        menu.addCommand(new RunExample("4", prg4.toString(), ctr4));
        menu.addCommand(new RunExample("5", prg5.toString(), ctr5));
        menu.show();
    }

    public static void main(String[] args) {
//        System.out.print("Give log file path: ");
//        var path = new Scanner(System.in).nextLine();
//        new UI(path).start();

        CommandRun();

    }
}