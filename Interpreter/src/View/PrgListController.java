package View;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrgListController implements Initializable {

    static Repo myFirstRepo, mySecondRepo, myThirdRepo, myFourthRepo, myLastRepo;
    static Controller myFirstController, mySecondController, myThirdController, myFourthController, myLastController;
    //static IStmt firstProgram, secondProgram, thirdProgram, fourthProgram, lastProgram;
    @FXML
    ListView<Controller> myPrgList;
    @FXML
    Button runButton;

    public void setUp() {
        myFirstRepo = new Repo("logs/firstProgramLog.txt");
        myFirstController = new Controller(myFirstRepo);
        mySecondRepo = new Repo("logs/secondProgramLog.txt");
        mySecondController = new Controller(mySecondRepo);
        myThirdRepo = new Repo("logs/thirdProgramLog.txt");
        myThirdController = new Controller(myThirdRepo);
        myFourthRepo = new Repo("logs/fourthProgramLog.txt");
        myFourthController = new Controller(myFourthRepo);
        myLastRepo = new Repo("logs/lastProgramLog.txt");
        myLastController = new Controller(myLastRepo);
        PrgState firstProgram = new PrgState(new CompStmt( new DeclStmt("v", new IntType()),
                new CompStmt(new DeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(
                                                new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(new PrintStmt(new HeapReadExp(new VariableExp("a"))),
                                                                        new PrintStmt(new VariableExp("v")))))),
                                                new CompStmt(new PrintStmt(new VariableExp("v")),
                                                        new PrintStmt(new HeapReadExp(new VariableExp("a"))))))))));
        PrgState secondProgram = new PrgState(new CompStmt(new DeclStmt(
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
        PrgState thirdProgram = new PrgState(new IfStmt(
                new RelExp(RelOp.HIGHER, new ValueExp(new IntValue(2)), new ValueExp(new IntValue(5))),
                new PrintStmt(new ValueExp(new IntValue(5))),
                new PrintStmt(new ValueExp(new IntValue(2)))));
        PrgState fourthProgram = new PrgState(new CompStmt(
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

        PrgState fifthProgram = new PrgState(new CompStmt( new DeclStmt("v", new IntType()),
                new CompStmt(new DeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(
                                                new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(new PrintStmt(new HeapReadExp(new VariableExp("a"))),
                                                                        new PrintStmt(new VariableExp("v")))))),
                                                new CompStmt(new PrintStmt(new VariableExp("v")),
                                                        new PrintStmt(new HeapReadExp(new VariableExp("a"))))))))));

        myFirstController.addProgramState(firstProgram);
        myFirstController.setName("ex 1");
        mySecondController.addProgramState(secondProgram);
        mySecondController.setName("ex 2");
        myThirdController.addProgramState(thirdProgram);
        myThirdController.setName("ex 3");
        myFourthController.addProgramState(fourthProgram);
        myFourthController.setName("ex 4");
        myLastController.addProgramState(fifthProgram);
        myLastController.setName("ex 5");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUp();
        ObservableList<Controller> myData = FXCollections.observableArrayList();
        myData.add(myFirstController);
        myData.add(mySecondController);
        myData.add(myThirdController);
        myData.add(myFourthController);
        myData.add(myLastController);
        myPrgList.setItems(myData);
        myPrgList.getSelectionModel().selectFirst();
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgRunController.class) {
                        return new PrgRunController(myPrgList.getSelectionModel().getSelectedItem());
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProgramLayout.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    programStage.setTitle("Toy Language - Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
