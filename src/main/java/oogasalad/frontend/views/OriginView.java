package oogasalad.frontend.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        ButtonFactory.makeButton("Play Game", event -> openPlayGame()),
        ButtonFactory.makeButton("Edit Game", event -> openEditGame())
  );
    Scene scene = new Scene(box);
    return scene;
  }
  private void openPlayGame() {
    getController().open(new GamePlayerView(new Stage(),getController()));
  }
  private void openEditGame() {
    getController().open(new GameEditorView(new Stage(),getController()));
  }

  @Override
  protected Scene refreshScene() {
    return getScene();
  }
}
