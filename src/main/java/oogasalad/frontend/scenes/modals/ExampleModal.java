package oogasalad.frontend.scenes.modals;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SceneController;

public class ExampleModal extends AbstractScene {

  public ExampleModal(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public void setText() {

  }

  @Override
  public Scene makeScene() {
    VBox box = new VBox(new Label("MODAL EXAMPLE"));
    Button button = new Button("Open a nested modal");
    //button.setOnAction(event -> this.showModal(new ExampleModal()));
    box.getChildren().add(button);
    setScene(new Scene(box));
    setText();
    setTheme();
    return getScene();
  }
}
