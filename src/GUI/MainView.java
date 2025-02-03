package GUI;

import Controller.Controller;
import View.Interpreter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class MainView extends Application {
    private static Interpreter interpreter;
    private static Stage stage;
    private static Scene selectProgramScene;
    private static Scene mainScene;
    private int programId;


    public static void main(String[] args) {
        interpreter = new Interpreter();
        launch(args);
    }

    @Override
    public void start(Stage newStage) throws Exception {

        stage = newStage;

        FXMLLoader setProgramLoader = new FXMLLoader();

        setProgramLoader.setControllerFactory(c -> new SelectProgramController());
        setProgramLoader.setLocation(getClass().getResource("SelectProgramController.fxml"));

        Parent setProgramRoot = setProgramLoader.load();
        SelectProgramController setProgramController = setProgramLoader.getController();

        selectProgramScene = new Scene(setProgramRoot);
        setProgramController.initialize();

        FXMLLoader setMainLoader = new FXMLLoader();

        setMainLoader.setControllerFactory(c -> new MainWindowController());
        setMainLoader.setLocation(getClass().getResource("MainWindowController.fxml"));

        Parent setMainRoot = setMainLoader.load();
        MainWindowController setMainController = setMainLoader.getController();

        mainScene = new Scene(setMainRoot);
        mainScene.setUserData(setMainLoader);

        newStage.setScene(selectProgramScene);
        newStage.setTitle("View.Interpreter MAP 2023 - 2024");
        newStage.show();
    }

    public static void switchToSelectProgramScene() {
        stage.setScene(selectProgramScene);
    }

    public static void switchToMainScene(int programId) {
        programId = programId;
        stage.setScene(mainScene);
        if (programId != -1) {
            FXMLLoader loader = (FXMLLoader)mainScene.getUserData();
            MainWindowController controller = loader.getController();
            controller.setSelectedProgram(interpreter.getProgram(programId));
        }
    }


}
