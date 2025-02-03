package GUI;

import Controller.Controller;
import Models.Statement.IStatement;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

import static HardcodedPrograms.HardcodedPrograms.hardcoded_programs;

public class SelectProgramController {
    private int programId = 0;

    public SelectProgramController(){
    }

    @FXML
    private ListView<String> programsListView;

    @FXML
    private Button selectProgramButton;

    @FXML
    private TextArea selectedProgramText;

    @FXML
    public void initialize() {
        programsListView.setItems(FXCollections.observableList(getListOfPrograms()));
        programsListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
            programId = Integer.parseInt(c.getList().get(0).replaceAll("\\D+",""))-1;
            selectedProgramText.setText(hardcoded_programs.get(programId).toString());
        });
        selectProgramButton.setOnAction(event -> {
            int index = programsListView.getSelectionModel().getSelectedIndex();
            if(index == -1){
                System.out.println("No program selected");
                return;
            }
            System.out.println("Selected program " + index);
            MainView.switchToMainScene(index);
        });

    }

    private ArrayList<String> getListOfPrograms() {
        ArrayList<String> list_of_programs = new ArrayList<>();
        for(int i=1;i<=hardcoded_programs.size();i++){
            list_of_programs.add("Program " + i);
        }
        return list_of_programs;
    }


}
