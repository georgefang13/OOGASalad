package oogasalad;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GamePlayerMainScene extends AbstractScene {
    public GamePlayerMainScene(AbstractWindow window) {
        super(window);
    }

    @Override
    public Scene makeScene() {
        BorderPane root = new BorderPane();
        root.setTop(new Label("Game Player"));
        root.setLeft(new VBox(new Button("Edit Grid")));
        setScene(new Scene(root));
        return getScene();
    }

}
