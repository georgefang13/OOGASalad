package oogasalad.windowscene.gameeditor;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.windowscene.AbstractScene;
import oogasalad.windowscene.AbstractWindow;
import oogasalad.windowscene.managers.PropertiesManager;

public class GameEditorEditorScene extends AbstractScene {
    private Button editGridButton;

    private Label gameEditorLabel;

    public GameEditorEditorScene(AbstractWindow window) {
        super(window);
    }

    @Override
    public Scene makeScene() {
        BorderPane root = new BorderPane();

        editGridButton = new Button();
        editGridButton.setOnAction(e -> getWindow().switchToScene("main"));

        gameEditorLabel = new Label();
        root.setTop(gameEditorLabel);
        root.setLeft(new VBox(editGridButton));
        setScene(new Scene(root));
        setText();
        setTheme();
        return getScene();
    }

    @Override
    public void setText() {
        editGridButton.setText(PropertiesManager.getText("GameEditorMainScene.EditGridButton"));
        gameEditorLabel.setText(PropertiesManager.getText("GameEditorMainScene.GameEditorLabel"));
    }

}
