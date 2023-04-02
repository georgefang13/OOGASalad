package oogasalad.frontend.views;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.controller.ViewController;

/**
 * The View Class is an abstraction for constructing new GUI Views.
 *
 * @author Connor Wells-Weiner
 * @author Owen MacKenzie
 */
public abstract class View {
  private ViewController controller;
  private Stage stage;
  private Scene scene;

  /**
   * View constructor makes scene and sets scene in stage.
   *
   * @param stage stage for displaying scene
   */

  protected View(Stage stage, ViewController controller) {
    this.controller = controller;
    scene = makeScene();
    this.stage = makeStage(stage);
  }

  /**
   * External API method to close stage.
   */
  public void close() {
    stage.close();
  }

  /**
   * External API method to open stage. Stage is refreshed with current content.
   */
  public void open() {
    stage.show();
    refreshStage();
  }

  /**
   * External API method to refresh content displayed in Stage.
   */

  public void refreshStage() {
    stage.setScene(refreshScene());
  }

  /**
   * Abstract method for refreshing the content in the scene.
   *
   * @return scene containing refreshed content
   */
  protected abstract Scene refreshScene();

  /**
   * Abstract method for making the scene with content.
   *
   * @return scene containing content
   */
  protected abstract Scene makeScene();

  /**
   * Method for making the stage with the scene
   *
   * @param stage stage to set scene
   * @return stage with scene set
   */

  protected Stage makeStage(Stage stage) {
    stage.setScene(scene);
    return stage;
  }

  protected Scene getScene() {
    return scene;
  }


}
