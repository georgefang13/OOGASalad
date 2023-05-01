package oogasalad.frontend.panels.gamePanels;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.scenes.GamePlayerMainScene;
import oogasalad.frontend.windows.GameWindow;

public class GameSetupPanel extends HBox implements Panel {
    PanelController panelController;
    public GameSetupPanel(PanelController panelController) {
        super();
        this.makePanel();
        this.panelController = panelController;
    }

    @Override
    public Panel makePanel() {
        Button btn = new Button("Start Local Game");
        btn.setOnAction(e -> {
            panelController.getSceneController().passData("local");
            panelController.newSceneFromPanel("GAME", GameWindow.WindowScenes.GAME_SCENE);
        });
        Button btn2 = new Button("Create Online Game");
        btn2.setOnAction(e -> {
            panelController.getSceneController().passData("create");
            panelController.newSceneFromPanel("GAME", GameWindow.WindowScenes.GAME_SCENE);
        });
        TextField codeField = new TextField();
        codeField.setPromptText("Enter Code");
        Button btn3 = new Button("Join Online Game");
        btn3.setOnAction(e -> {
            String code = codeField.getText();
            panelController.getSceneController().passData(code);
            panelController.getSceneController().passData("join");
            panelController.newSceneFromPanel("GAME", GameWindow.WindowScenes.GAME_SCENE);
        });
        this.getChildren().addAll(btn, btn2, codeField, btn3);
        return this;
    }

    @Override
    public void refreshPanel() {}

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void save() {

    }
}
