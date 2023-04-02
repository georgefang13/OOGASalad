package oogasalad.frontend.views;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.controller.ViewController;

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
    return new Scene(new VBox());
  }

  @Override
  protected Scene refreshScene() {
    return getScene();
  }
}
