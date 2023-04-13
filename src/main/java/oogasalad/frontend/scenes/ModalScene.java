package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import oogasalad.frontend.modals.subDisplayModals.AlertModal;
import oogasalad.frontend.modals.subInputModals.CreateNewModal;

/**
 * @author George Fang
 * @author Joao Carvalho
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class ModalScene extends AbstractScene {

  private Button createGameButton, errorButton;

  public ModalScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    VBox root = new VBox();
    createGameButton = new Button();
    createGameButton.setOnAction(e -> {
      CreateNewModal modal = new CreateNewModal("Create_Game_Modal");
      modal.showAndWait();
    });
    errorButton = new Button();
    errorButton.setOnAction(e -> {
      AlertModal modal = new AlertModal();
      modal.showAndWait();
    });
    root.getChildren().addAll(errorButton, createGameButton);
    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }

  @Override
  public void setText() {
    createGameButton.setText(getPropertyManager().getText("ModalScene.CreateGameButton"));
    errorButton.setText(getPropertyManager().getText("ModalScene.ErrorButton"));
  }
}

