package oogasalad.frontend.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.controller.ViewController;
import oogasalad.frontend.components.factories.ButtonFactory;

public class OriginView extends View {

  /**
   * View constructor makes scene and sets scene in stage.
   *
   * @param stage      stage for displaying scene
   * @param controller
   */
  public OriginView(Stage stage, ViewController controller) {
    super(stage, controller);
  }

  @Override
  protected Scene makeScene() {
    VBox box = new VBox();
    box.getChildren().addAll(
        ButtonFactory.makeButton("Play Game", null),
        ButtonFactory.makeButton("Edit Game", null));
    Scene scene = new Scene(box);
    return scene;
  }

  @Override
  protected Scene refreshScene() {
    return getScene();
  }
}
