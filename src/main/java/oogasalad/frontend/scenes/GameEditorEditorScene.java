package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.managers.PropertiesManager;

import java.util.HashMap;
import java.util.Map;

public class GameEditorEditorScene extends AbstractScene {
    private Button editGridButton;
    private Label gameEditorLabel;
    private BorderPane root;
    private VBox leftTab; //REPLACE WITH A PANEL
    private VBox visualPanel; //REPLACE WITH A PANEL
    private Map<Button,VBox> buttonVBoxMap;

    public GameEditorEditorScene(SceneController sceneController) {
        super(sceneController);
    }

    private void setButtonVisualPanel(Button button, String title){
        VBox newVisualPanel = new VBox();
        newVisualPanel.getChildren().add(new Label(title));
        button.setOnAction(e -> updateVisualPanel(button));
        buttonVBoxMap.put(button,newVisualPanel);
    }

    private void updateVisualPanel(Button button){
        System.out.print(button.getText());
        visualPanel = buttonVBoxMap.get(button);
        refreshScene();
        //sceneController.wirefreshScene();
    }


    @Override
    public Scene makeScene() {
        root = new BorderPane();

        //left sidebar

        leftTab = new VBox();

        buttonVBoxMap = new HashMap<>();

        Button boardButton = new Button("Board");
        setButtonVisualPanel(boardButton, "Board");
        Button variableButton = new Button("Variable");
        setButtonVisualPanel(variableButton, "Variable");
        Button playerButton = new Button("Player");
        setButtonVisualPanel(playerButton, "Player");
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> sceneController.switchToScene("main"));

        leftTab.getChildren().addAll(boardButton,variableButton,playerButton,backButton);

        //main body

        visualPanel = new VBox();
        Label mainLabel = new Label("MAIN");
        visualPanel.getChildren().add(mainLabel);

        //put it together

        refreshScene();
        return getScene();
    }
    private void refreshScene(){
        root.setLeft(leftTab);
        root.setCenter(visualPanel);
        setScene(new Scene(root));
        setText();
        setTheme();
    }

    @Override
    public void setText() {
        //need this
    }

}
