package oogasalad.windowscene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameEditorEditorScene extends AbstractScene{

    public GameEditorEditorScene(AbstractWindow window) {
        super(window);
    }

    @Override
    public Scene makeScene() {
        BorderPane root = new BorderPane();
        Button backButton = new Button("BACK"); //TODO: Properties File
        backButton.setOnAction(e -> getWindow().switchToScene("main"));
        root.setTop(new Label("SWITCHED TO THE EDITOR EDIT SCENE"));
        root.setLeft(new VBox(backButton));

        setScene(new Scene(root));
        return getScene();
    }
}
