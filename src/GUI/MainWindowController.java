package GUI;

import Controller.Controller;
import Models.Value.IntValue;
import Models.Value.Value;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainWindowController {
    Controller controller;

    private int selectedProgramStateId = 0;

    public MainWindowController(){

    }


    @FXML
    private Button backButton;


    @FXML
    private Button oneStepButton;

    @FXML
    private Button allStepButton;


    @FXML
    private Text ProgramStatesCounter;

    @FXML
    private ListView programStatesListView;

    @FXML
    private ListView OutListView;

    @FXML
    private ListView ExecutionStackListView;


    @FXML
    private ListView fileTableListView;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTable;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> NameColumn;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> ValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> HeapValueColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> HeapAdressColumn;

    @FXML
    private TableView<Map.Entry<Integer, Pair<IntValue, List<IntValue>>>> semaphoreTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<IntValue, List<IntValue>>>, String> semaphoreIndexColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<IntValue, List<IntValue>>>, String> semaphoreSizeColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<IntValue, List<IntValue>>>, String> semaphoreListColumn;

    @FXML
    public void initialize() {
        backButton.setOnAction(e -> {
            MainView.switchToSelectProgramScene();
        });
        oneStepButton.setOnAction(e -> {
            if(controller.getNrOfProgramStates()==0) {
                return;
            }
            controller.oneStepForAllPrg();
            updateProgramState();
        });
        allStepButton.setOnAction(e -> {
            if(controller.getNrOfProgramStates()==0) {
                return;
            }
            controller.allStep();
            updateProgramState();
        });

        NameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        ValueColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Value>(cellData.getValue().getValue()));

        HeapAdressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().toString()));
        HeapValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        semaphoreIndexColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().toString()));
        semaphoreSizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getKey().toString()));
        semaphoreListColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getValue().toString()));


        programStatesListView.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue!=null &&  newValue.intValue() >=0){

                System.out.println("here:"+newValue.toString());
                selectedProgramStateId = newValue.intValue();
                updateProgramState();
            }
            //selectedProgramStateId = Integer.parseInt(programStatesListView.getSelectionModel().getSelectedItem().toString());
        }));

    }

    private List<String> getProgramStates(){
        List<String> programStates = new ArrayList<>();
        int nrOfProgramStates=controller.getNrOfProgramStates();
        for(int i=0;i<nrOfProgramStates;i++){
            programStates.add("Program State "+i);
        }
        return programStates;
    }

    public void setSelectedProgram(Controller controller){
        this.controller = controller;
        updateProgramState();
        controller.initialize();
    }

    private void updateProgramState(){

        //this.symbolTable.getItems().clear();

        ProgramStatesCounter.setText("Nr of program states: "+Integer.toString(controller.getNrOfProgramStates()));
        programStatesListView.getItems().clear();
        programStatesListView.setItems(FXCollections.observableList(getProgramStates()));
        if(controller.getNrOfProgramStates()==0)
            return;
        OutListView.setItems(FXCollections.observableList(controller.getOutput().getOutputList().getAll()));
        fileTableListView.setItems(FXCollections.observableList(controller.getFileTable().toList()));
        symbolTable.getItems().setAll((controller.getSymbolTableOfProgramState(selectedProgramStateId).getContent().entrySet()));
        ExecutionStackListView.setItems(FXCollections.observableList(controller.getExecutionStackOfProgramState(selectedProgramStateId).getReversed()));
        heapTable.setItems(FXCollections.observableArrayList(controller.getHeapTableOfProgramState(selectedProgramStateId).getContent().entrySet()));
        semaphoreTable.setItems(FXCollections.observableArrayList(controller.getSemaphoreTable().getContent().entrySet()));
    }


}
