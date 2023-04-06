package oogasalad.windowscene.modal;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import oogasalad.gameeditor.frontend.components.modals.SubDisplayModals.AlertModal;
import oogasalad.gameeditor.frontend.components.modals.SubInputModals.CreateGameModal;
import oogasalad.windowscene.AbstractScene;
import oogasalad.windowscene.AbstractWindow;
import oogasalad.windowscene.managers.PropertiesManager;

/**
 * @author George Fang
 * @author Joao Carvalho
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class ModalScene extends AbstractScene {

    public ModalScene(AbstractWindow window) {
        super(window);
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

