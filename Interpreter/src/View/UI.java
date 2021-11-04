package View;


import Controller.Controller;
import Model.Expressions.ArithExp;
import Model.Expressions.ArithOp;
import Model.Expressions.ValueExp;
import Model.Expressions.VariableExp;
import Model.PrgState;
import Model.Statements.*;
import Types.BoolType;
import Types.IntType;
import Values.BoolValue;
import Values.IntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private final Controller controller = new Controller();
    public List<IStmt> list = new ArrayList<>();

    void addExamples() {
        this.list.add(new CompStmt(new DeclStmt(
                "v",
                new IntType()),
                new CompStmt(new AssignStmt(
                        "v",
                        new ValueExp(new IntValue(2))),
                        new PrintStmt(new VariableExp("v")))));

        this.list.add(new CompStmt(new DeclStmt(
                "a",
                new IntType()),
                new CompStmt(new DeclStmt(
                        "b",
                        new IntType()),
                        new CompStmt(new AssignStmt(
                                "a",
                                new ArithExp(
                                        ArithOp.ADDITION,
                                        new ValueExp(new IntValue(2)),
                                        new ArithExp(
                                                ArithOp.MULTIPLICATION,
                                                new ValueExp(new IntValue(3)),
                                                new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt(
                                        "b",
                                        new ArithExp(
                                                ArithOp.ADDITION,
                                                new VariableExp("a"),
                                                new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VariableExp("b")))))));

        this.list.add(new CompStmt(new DeclStmt(
                "a",
                new BoolType()),
                new CompStmt(new DeclStmt(
                        "v",
                        new IntType()),
                        new CompStmt(new AssignStmt(
                                "a",
                                new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(
                                        new VariableExp("a"),
                                        new AssignStmt(
                                                "v",
                                                new ValueExp(new IntValue(2))),
                                        new AssignStmt(
                                                "v",
                                                new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VariableExp("v")))))));

        this.list.add(new CompStmt(new DeclStmt(
                "a",
                new IntType()),
                new CompStmt(new DeclStmt(
                        "v",
                        new BoolType()),
                        new CompStmt(new AssignStmt(
                                "a",
                                new ValueExp(new IntValue(1))),
                                new CompStmt(new AssignStmt(
                                        "v",
                                        new ValueExp(new BoolValue(false))),
                                        new CompStmt(new IfStmt(
                                                new VariableExp("v"),
                                                new AssignStmt(
                                                        "a",
                                                        new ArithExp(
                                                                ArithOp.ADDITION,
                                                                new VariableExp("a"),
                                                                new ArithExp(
                                                                        ArithOp.MULTIPLICATION,
                                                                        new ValueExp(new IntValue(3)),
                                                                        new ValueExp(new IntValue(5))))),
                                                new AssignStmt(
                                                        "a",
                                                        new ValueExp(new IntValue(0)))),
                                                new PrintStmt(new VariableExp("a"))))))));
    }

    void printExamples() {
        System.out.print("""
                Example1:
                    int v
                    v = 2
                    print(v)
                    
                Example2:
                    int a
                    int b
                    a = 2 + 3 * 5
                    b = a + 1
                    print(b)
                    
                Example3:
                    bool a
                    int v
                    a = true
                    if a {
                        v = 2
                    } else {
                        v = 3
                    }
                    print(v)
                    
                Example4:
                    int a
                    bool v
                    a = 1
                    v = false
                    if v {
                        a = a + 2 * 3
                    } else {
                        a = 0
                    }
                    print(a)
                """);
    }


    public void start() {
        addExamples();

        while (true) {
            System.out.print("""
                    0. exit
                    1. choose example
                    """);
            var option = new Scanner(System.in).nextInt();
            switch (option) {
                case 0 -> System.exit(0);
                case 1 -> {
                    this.printExamples();

                    var option1 = new Scanner(System.in).nextInt();

                    if (option1 > 4 || option1 < 1) {
                        System.out.println("Invalid option");
                        return;
                    }

                    this.controller.addProgramState(new PrgState(this.list.get(option1 - 1)));
                    try {
                        System.out.println(this.controller.allStep());
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                }
                default -> System.out.println("Invalid option");
            }
        }
    }
}
