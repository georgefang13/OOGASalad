package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import oogasalad.frontend.modals.subDisplayModals.AlertModal;
import oogasalad.frontend.modals.subInputModals.CreateGameModal;
import oogasalad.frontend.windows.AbstractWindow;

/**
 * @author George Fang
 * @author Joao Carvalho
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class ModalScene extends AbstractScene {

    public ModalScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public Scene makeScene() {
        VBox root = new VBox();
        Button button = new Button("Create Game Modal");
        button.setOnAction(e -> {
            CreateGameModal modal = new CreateGameModal();
            modal.showAndWait();
        });
        root.getChildren().add(button);
        Button errorButton = new Button("Create Error Modal");
        errorButton.setOnAction(e -> {
            AlertModal modal = new AlertModal();
            modal.showAndWait();
        });
        root.getChildren().add(errorButton);
        setScene(new Scene(root));
        setText();
        setTheme();
        return getScene();
    }

    @Override
    public void setText() {
    }
}

