package View;

import Controller.Controller;
import Controller.Exceptions.InvalidIndexException;
import Controller.Exceptions.MyException;
import Controller.Exceptions.NoPrgStatesException;
import Model.Containers.MyList;
import Model.Containers.Tuple;
import Model.PrgState;
import Model.Statements.IStmt;
import Values.IValue;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PrgRunController implements Initializable {

    Controller myController;
    @FXML
    Label nrPrgStates;
    @FXML
    Button runButton;
    @FXML
    TableView<Map.Entry<Integer, IValue>> heapTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Integer>, String> heapTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,IValue>, String> heapTableValue;
    @FXML
    ListView<String> outList;
    @FXML
    TableView<Map.Entry<String, BufferedReader>> fileTable;
    @FXML
    TableColumn<HashMap.Entry<String, Tuple<String, BufferedReader>>, String> fileTableIdentifier;
    @FXML
    TableColumn<HashMap.Entry<String, BufferedReader>, String> fileTableFileName;
    @FXML
    ListView<String> prgStateList;
    @FXML
    TableView<Map.Entry<String, IValue>> symTable;
    @FXML
    TableColumn<HashMap.Entry<String, Integer>, String> symTableVariable;
    @FXML
    TableColumn<HashMap.Entry<String, IValue>, String> symTableValue;
    @FXML
    ListView<String> exeStackList;

    public PrgRunController(Controller myController) {
        myController.startStepGUI();
        this.myController = myController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initialRun();
        } catch (NoPrgStatesException | InvalidIndexException e) {
            e.printStackTrace();
        }
        prgStateList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    setSymTableAndExeStack();
                } catch (NoPrgStatesException e) {
                    e.printStackTrace();
                }
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                try {

                        myController.oneStepGUI();
//                    myController.allStep();
//                    myController.allStepGUI();
                } catch (MyException | IOException | CloneNotSupportedException e1) {
                    System.out.print(e1);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Toy Language - Current program finished");
                    alert.setHeaderText(null);
                    alert.setContentText("Program successfully finished!");
                    Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    Stage stage = (Stage) heapTable.getScene().getWindow();
                    stage.close();
                }
                try {
                    heapTable.refresh();
                    outList.refresh();
                    fileTable.refresh();
                    prgStateList.refresh();

                    updateUIComponents();
                } catch (NoPrgStatesException ex) {
                    ex.printStackTrace();
                } catch (InvalidIndexException completed_ex) {
                    System.out.print(completed_ex);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Toy Language - Current program finished");
                    alert.setHeaderText(null);
                    alert.setContentText("Program successfully finished!");
                    Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    Stage stage = (Stage) heapTable.getScene().getWindow();
                    stage.close();
//                    completed_ex.printStackTrace();
                }
            }
        });
    }

    public void initialRun() throws NoPrgStatesException, InvalidIndexException {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        prgStateList.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
    }

    public void updateUIComponents() throws NoPrgStatesException, InvalidIndexException {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        if(prgStateList.getSelectionModel().getSelectedItem() == null) {
            prgStateList.getSelectionModel().selectFirst();
        }
        setSymTableAndExeStack();
    }

    public void setNumberLabel() throws NoPrgStatesException {
        nrPrgStates.setText("The number of PrgStates: " + myController.getRepo().getPrgList().size());
    }

    public void setHeapTable() throws NoPrgStatesException, InvalidIndexException {
        ObservableList<HashMap.Entry<Integer, IValue>> heapTableList = FXCollections.observableArrayList();
        heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
//        System.out.print(myController.getRepo().getPrgList());
        heapTableList.addAll(myController.getRepo().getPrgList().get(0).getHeap().entrySet());
//        heapTableList.addAll(myController.getRepo().getPrgList().get(0).getHeap().entrySet());
        heapTable.setItems(heapTableList);
    }

    public void setOutList() throws NoPrgStatesException, InvalidIndexException {
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        for(IValue e : myController.getRepo().getPrgList().get(0).getPrintBuffer()) {
            outObservableList.add(e.toString());
        }
        outList.setItems(outObservableList);
    }

    public void setFileTable() throws NoPrgStatesException, InvalidIndexException {
        ObservableList<HashMap.Entry<String, BufferedReader>> fileTableList = FXCollections.observableArrayList();
        fileTableIdentifier.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        fileTableFileName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        fileTableList.addAll(myController.getRepo().getPrgList().get(0).getFileTable().entrySet());
        fileTable.setItems(fileTableList);
    }

    public void setPrgStateList() throws NoPrgStatesException {
        ObservableList<String> prgStateIdList = FXCollections.observableArrayList();
        for(PrgState e : myController.getRepo().getPrgList()) {
            prgStateIdList.add(Integer.toString(e.getId()));
        }
        prgStateList.setItems(prgStateIdList);
    }

    public void setSymTableAndExeStack() throws NoPrgStatesException {
        ObservableList<HashMap.Entry<String, IValue>> symTableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();
        symTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        MyList<PrgState> allPrgs = myController.getRepo().getPrgList();
        PrgState prgResult = null;
        for(PrgState e : allPrgs) {
            if(e.getId() == Integer.parseInt(prgStateList.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }
        if(prgResult != null) {
            symTableList.addAll(prgResult.getSymbolTable().entrySet());
            for(IStmt e : prgResult.getExecutionStack().getStack()) {
                exeStackItemsList.add(e.toString());
            }
            symTable.setItems(symTableList);
            exeStackList.setItems(exeStackItemsList);
        }
    }

}
