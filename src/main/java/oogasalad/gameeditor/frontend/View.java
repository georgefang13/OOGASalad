package oogasalad.gameeditor.frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class View {
  private Stage stage;
  private Scene scene;

  public void openStage() { stage.show(); }
  public void closeStage() { stage.close(); }
  public void refreshStage() { stage.setScene(refreshScene()); }
  abstract Scene makeScene();
  abstract Scene refreshScene();

}